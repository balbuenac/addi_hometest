package com.addi.app;

import com.addi.app.mapper.Mapper;
import com.addi.common.enums.Status;
import com.addi.common.exceptions.LeadException;
import com.addi.common.request.LeadRequest;
import com.addi.common.response.LeadResponse;
import com.addi.steps.internal.InternalScoreStep;
import com.addi.steps.internal.response.InternalScoreResponse;
import com.addi.steps.judicial.JudicialRegistryStep;
import com.addi.steps.judicial.response.JudicialRegistryResponse;
import com.addi.steps.national.NationalRegistryStep;
import com.addi.steps.national.response.NationalRegistryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

import static com.addi.common.constants.Constants.MAX_THREADS;
import static com.addi.common.constants.Constants.MIN_FOR_APPROVAL;

@Service
public class LeadEvaluator {

    private Logger LOG = LoggerFactory.getLogger(LeadEvaluator.class);

    private InternalScoreStep scoreStep;
    private JudicialRegistryStep judicialStep;
    private NationalRegistryStep nationalStep;

    public LeadEvaluator(InternalScoreStep scoreStep,
                         JudicialRegistryStep judicialStep,
                         NationalRegistryStep nationalStep) {
        this.scoreStep = scoreStep;
        this.judicialStep = judicialStep;
        this.nationalStep = nationalStep;
    }

    public LeadResponse evaluate(LeadRequest request) {
        LOG.info("Evaluating Request [{}]", request);
        LeadResponse response = Mapper.buildLeadResponse(request);
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        try {

            Future<NationalRegistryResponse> futureNationalRegistryResponse = executor.submit(buildCallableForNationalRegistry(request));
            Future<JudicialRegistryResponse> futureJudicialRegistryResponse = executor.submit(buildCallableForJudicialRegistry(request));

            NationalRegistryResponse nationalRegistryResponse = futureNationalRegistryResponse.get();
            JudicialRegistryResponse judicialRegistryResponse = futureJudicialRegistryResponse.get();

            LOG.info("Evaluating Results :: Judicial [{}] National [{}]", judicialRegistryResponse, nationalRegistryResponse);
            response.setStatus(Status.Rejected);
            if (nationalRegistryResponse.isValid() && judicialRegistryResponse.isValid()) {
                InternalScoreResponse internalScoreResponse = this.scoreStep.evaluate(Mapper.mapToInternalScoreRequest(request));
                if (internalScoreResponse != null && internalScoreResponse.getScore() >= MIN_FOR_APPROVAL) {
                    LOG.info("Evaluating Results :: Score [{}]", internalScoreResponse.getScore());
                    response.setScore(internalScoreResponse.getScore());
                    LOG.info("Evaluating Results for Request [{}] :: APPROVED", request);
                    response.setStatus(Status.Approved);
                }
            }
            LOG.info("Evaluating Results for Request [{}] :: APPROVED", request);
            return response;
        }
        catch (LeadException e) {
            LOG.error("Evaluating Results for Request [{}] and LeadException [{}]:: FAILED", request, e.getMessage());
            response.setStatus(Status.FailedWithErrors);
            return response;
        } catch (Exception e) {
            LOG.error("Evaluating Results for Request [{}] and Exception [{}]:: FAILED", request, e.getMessage());
            response.setStatus(Status.FailedWithErrors);
            return response;
        }
    }

    private Callable<NationalRegistryResponse> buildCallableForNationalRegistry(LeadRequest request) {
        return () -> nationalStep.evaluate(Mapper.mapToNationalRegistryRequest(request));
    }

    private Callable<JudicialRegistryResponse> buildCallableForJudicialRegistry(LeadRequest request) {
        return () -> judicialStep.evaluate(Mapper.mapToJudicialRegistryRequest(request));
    }

}
