package com.addi.app;

import com.addi.common.enums.Status;
import com.addi.common.exceptions.LeadException;
import com.addi.common.request.LeadRequest;
import com.addi.common.response.LeadResponse;
import com.addi.steps.internal.InternalScoreStep;
import com.addi.steps.judicial.JudicialRegistryStep;
import com.addi.steps.national.NationalRegistryStep;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;
import org.testng.annotations.*;

@ExtendWith(MockitoExtension.class)
public class LeadEvaluatorTest {

    private NationalRegistryStep nationalStep = Mockito.mock(NationalRegistryStep.class);
    private JudicialRegistryStep judicialStep = Mockito.mock(JudicialRegistryStep.class);
    private InternalScoreStep scoreStep = Mockito.mock(InternalScoreStep.class);
    private LeadEvaluator evaluator = new LeadEvaluator(scoreStep, judicialStep, nationalStep);

    @AfterMethod
    private void setup() {
        Mockito.reset(scoreStep);
        Mockito.reset(judicialStep);
        Mockito.reset(nationalStep);
    }

    @Test
    public void testEvaluation_Success() {
        LeadRequest request = Generator.buildLeadRequest();
        Mockito.when(scoreStep.evaluate(Mockito.any())).thenReturn(Generator.buldInternalScoreResponse(80));
        Mockito.when(judicialStep.evaluate(Mockito.any())).thenReturn(Generator.buildJudicialRegistryResponse(true));
        Mockito.when(nationalStep.evaluate(Mockito.any())).thenReturn(Generator.buildNationalRegistryResponse(true));
        LeadResponse response = evaluator.evaluate(request);
        Assert.assertEquals(response.getStatus(), Status.Approved);
    }

    @Test
    public void testEvaluationRejected_whenJudicialProcessExists() {
        LeadRequest request = Generator.buildLeadRequest();
        Mockito.when(scoreStep.evaluate(Mockito.any())).thenReturn(Generator.buldInternalScoreResponse(80));
        Mockito.when(judicialStep.evaluate(Mockito.any())).thenReturn(Generator.buildJudicialRegistryResponse(false));
        Mockito.when(nationalStep.evaluate(Mockito.any())).thenReturn(Generator.buildNationalRegistryResponse(true));
        LeadResponse response = evaluator.evaluate(request);
        Assert.assertEquals(response.getStatus(), Status.Rejected);
    }

    @Test
    public void testEvaluationRejected_whenIdentityIsNotCorrect() {
        LeadRequest request = Generator.buildLeadRequest();
        Mockito.when(scoreStep.evaluate(Mockito.any())).thenReturn(Generator.buldInternalScoreResponse(80));
        Mockito.when(judicialStep.evaluate(Mockito.any())).thenReturn(Generator.buildJudicialRegistryResponse(false));
        Mockito.when(nationalStep.evaluate(Mockito.any())).thenReturn(Generator.buildNationalRegistryResponse(false));
        LeadResponse response = evaluator.evaluate(request);
        Assert.assertEquals(response.getStatus(), Status.Rejected);
    }

    @Test
    public void testEvaluationRejected_whenScoreIsLowerThanMinimum() {
        LeadRequest request = Generator.buildLeadRequest();
        Mockito.when(scoreStep.evaluate(Mockito.any())).thenReturn(Generator.buldInternalScoreResponse(50));
        Mockito.when(judicialStep.evaluate(Mockito.any())).thenReturn(Generator.buildJudicialRegistryResponse(true));
        Mockito.when(nationalStep.evaluate(Mockito.any())).thenReturn(Generator.buildNationalRegistryResponse(true));
        LeadResponse response = evaluator.evaluate(request);
        Assert.assertEquals(response.getStatus(), Status.Rejected);
    }

    @Test
    public void testEvaluationFailed_whenJudicialSystemIsDown() {
        LeadRequest request = Generator.buildLeadRequest();
        Mockito.when(scoreStep.evaluate(Mockito.any())).thenReturn(Generator.buldInternalScoreResponse(50));
        Mockito.when(judicialStep.evaluate(Mockito.any())).thenThrow(new LeadException());
        Mockito.when(nationalStep.evaluate(Mockito.any())).thenReturn(Generator.buildNationalRegistryResponse(true));
        LeadResponse response = evaluator.evaluate(request);
        Assert.assertEquals(response.getStatus(), Status.FailedWithErrors);
    }

    @Test
    public void testEvaluationFailed_whenNationalSystemIsDown() {
        LeadRequest request = Generator.buildLeadRequest();
        Mockito.when(scoreStep.evaluate(Mockito.any())).thenReturn(Generator.buldInternalScoreResponse(50));
        Mockito.when(judicialStep.evaluate(Mockito.any())).thenReturn(Generator.buildJudicialRegistryResponse(true));
        Mockito.when(nationalStep.evaluate(Mockito.any())).thenThrow(new LeadException());
        LeadResponse response = evaluator.evaluate(request);
        Assert.assertEquals(response.getStatus(), Status.FailedWithErrors);
    }

    @Test
    public void testEvaluationFailed_whenInternalSystemIsDown() {
        LeadRequest request = Generator.buildLeadRequest();
        Mockito.when(scoreStep.evaluate(Mockito.any())).thenThrow(new LeadException());
        Mockito.when(judicialStep.evaluate(Mockito.any())).thenReturn(Generator.buildJudicialRegistryResponse(true));
        Mockito.when(nationalStep.evaluate(Mockito.any())).thenReturn(Generator.buildNationalRegistryResponse(true));
        LeadResponse response = evaluator.evaluate(request);
        Assert.assertEquals(response.getStatus(), Status.FailedWithErrors);
    }

}
