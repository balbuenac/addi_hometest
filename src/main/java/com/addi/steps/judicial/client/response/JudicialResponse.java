package com.addi.steps.judicial.client.response;

public class JudicialResponse {
    private boolean hasRecords;

    public JudicialResponse() {}

    public JudicialResponse(boolean hasRecords) {
        this.hasRecords = hasRecords;
    }

    public boolean hasRecords() {
        return hasRecords;
    }

    public void setHasRecords(boolean hasRecords) {
        this.hasRecords = hasRecords;
    }
}
