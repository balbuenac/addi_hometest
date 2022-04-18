package com.addi.steps.internal.request;

public class InternalScoreRequest {
    private String identificationNumber;

    public InternalScoreRequest() {
    }

    public InternalScoreRequest(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }
}
