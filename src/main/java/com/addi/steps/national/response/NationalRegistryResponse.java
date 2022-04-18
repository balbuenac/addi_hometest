package com.addi.steps.national.response;

public class NationalRegistryResponse {
    private boolean isValid;

    public NationalRegistryResponse() {

    }

    public NationalRegistryResponse(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
