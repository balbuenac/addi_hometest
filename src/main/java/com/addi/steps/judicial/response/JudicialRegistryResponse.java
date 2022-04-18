package com.addi.steps.judicial.response;

public class JudicialRegistryResponse {
    private boolean isValid;

    public JudicialRegistryResponse() {
    }

    public JudicialRegistryResponse(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
