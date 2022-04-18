package com.addi.steps.judicial;

import com.addi.steps.judicial.client.JudicialRegistryClient;
import com.addi.steps.judicial.client.response.JudicialResponse;
import com.addi.steps.judicial.mapper.Mapper;
import com.addi.steps.judicial.request.JudicialRegistryRequest;
import com.addi.steps.judicial.response.JudicialRegistryResponse;
import org.springframework.stereotype.Service;

@Service
public class JudicialRegistryStep {

    private JudicialRegistryClient client;

    public JudicialRegistryStep(JudicialRegistryClient client) {
        this.client = client;
    }

    public JudicialRegistryResponse evaluate(JudicialRegistryRequest request) {
        JudicialResponse response = client.verify(Mapper.mapToJudicialRequest(request));
        if (response.hasRecords() == true) {
            return new JudicialRegistryResponse(true);
        }
        return new JudicialRegistryResponse(false);
    }
}
