package com.addi.steps.judicial.request;

public class JudicialRegistryRequest {
    private String identificationNumber;

    public JudicialRegistryRequest() {
    }

    public JudicialRegistryRequest(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }
}
