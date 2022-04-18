package com.addi.steps.national.request;

import com.addi.common.request.Person;

public class NationalRegistryRequest {
    private Person person;

    public NationalRegistryRequest() {

    }

    public NationalRegistryRequest(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
