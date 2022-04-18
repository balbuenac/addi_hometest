package com.addi.steps.national.mapper;

import com.addi.steps.national.client.request.NationalRequest;
import com.addi.steps.national.request.NationalRegistryRequest;

public class Mapper {

    public static NationalRequest mapToNationalRequest(NationalRegistryRequest request) {
        NationalRequest nationalRequest = new NationalRequest();
        nationalRequest.setIdentificationNumber(request.getPerson().getIdentificationNumber());
        return nationalRequest;
    }

}
