package com.addi.steps.national;

import com.addi.common.request.Person;
import com.addi.steps.national.client.NationalRegistryClient;
import com.addi.steps.national.client.response.NationalResponse;
import com.addi.steps.national.mapper.Mapper;
import com.addi.steps.national.request.NationalRegistryRequest;
import com.addi.steps.national.response.NationalRegistryResponse;
import org.springframework.stereotype.Service;

@Service
public class NationalRegistryStep {

    private NationalRegistryClient client;

    public NationalRegistryStep(NationalRegistryClient client) {
        this.client = client;
    }

    public NationalRegistryResponse evaluate(NationalRegistryRequest request) {
        NationalResponse response = client.verify(Mapper.mapToNationalRequest(request));
        Person person = request.getPerson();
        if (response != null) {
            if (response.getFirstName().equals(person.getFirstName()) &&
                    response.getLastName().equals(person.getLastName()) &&
                    response.getIdentificationNumber().equals(person.getIdentificationNumber())) {
                return new NationalRegistryResponse(true);
            }
        }
        return new NationalRegistryResponse(false);
    }

}
