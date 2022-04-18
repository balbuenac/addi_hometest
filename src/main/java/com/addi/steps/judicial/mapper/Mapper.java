package com.addi.steps.judicial.mapper;

import com.addi.steps.judicial.client.request.JudicialRequest;
import com.addi.steps.judicial.request.JudicialRegistryRequest;

public class Mapper {
    public static JudicialRequest mapToJudicialRequest(JudicialRegistryRequest request) {
        JudicialRequest judicialRequest = new JudicialRequest();
        judicialRequest.setIdentificationNumber(request.getIdentificationNumber());
        return judicialRequest;
    }
}
