package com.addi.app.mapper;

import com.addi.common.enums.Status;
import com.addi.common.request.LeadRequest;
import com.addi.common.response.LeadResponse;
import com.addi.steps.internal.request.InternalScoreRequest;
import com.addi.steps.judicial.request.JudicialRegistryRequest;
import com.addi.steps.national.request.NationalRegistryRequest;

public class Mapper {

    public static NationalRegistryRequest mapToNationalRegistryRequest(LeadRequest request) {
        return new NationalRegistryRequest(request.getPerson());
    }

    public static JudicialRegistryRequest mapToJudicialRegistryRequest(LeadRequest request) {
        return new JudicialRegistryRequest(request.getPerson().getIdentificationNumber());
    }

    public static InternalScoreRequest mapToInternalScoreRequest(LeadRequest request) {
        return new InternalScoreRequest(request.getPerson().getIdentificationNumber());
    }

    public static LeadResponse buildLeadResponse(LeadRequest request) {
        LeadResponse response = new LeadResponse();
        response.setIdentificationNumber(request.getPerson().getIdentificationNumber());
        response.setFirstName(request.getPerson().getFirstName());
        response.setLastName(request.getPerson().getLastName());
        response.setStatus(Status.InEvaluation);
        response.setScore(0);
        return response;
    }

}
