package com.addi.app;

import com.addi.common.enums.Status;
import com.addi.common.request.LeadRequest;
import com.addi.common.request.Person;
import com.addi.steps.internal.request.InternalScoreRequest;
import com.addi.steps.internal.response.InternalScoreResponse;
import com.addi.steps.judicial.client.response.JudicialResponse;
import com.addi.steps.judicial.request.JudicialRegistryRequest;
import com.addi.steps.judicial.response.JudicialRegistryResponse;
import com.addi.steps.national.client.response.NationalResponse;
import com.addi.steps.national.request.NationalRegistryRequest;
import com.addi.steps.national.response.NationalRegistryResponse;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class Generator {

    private static BigInteger ID = BigInteger.TEN;
    private static String FIRST_NAME = "Carlos";
    private static String LAST_NAME = "Smith";
    private static String IDENTIFICATION_NUMBER = "1111111";
    private static int AGE = 30;
    private static LocalDateTime BIRTH_DATE = LocalDateTime.now().minusYears(AGE);

    public static LeadRequest buildLeadRequest() {
        LeadRequest request = new LeadRequest();
        request.setId(ID);
        request.setStatus(Status.InEvaluation);
        request.setPerson(buildPerson());
        return request;
    }

    public static Person buildPerson() {
        Person person = new Person();
        person.setFirstName(FIRST_NAME);
        person.setLastName(LAST_NAME);
        person.setIdentificationNumber(IDENTIFICATION_NUMBER);
        person.setAge(AGE);
        person.setBirthDate(BIRTH_DATE);
        return person;
    }

    public static NationalRegistryResponse buildNationalRegistryResponse(boolean isValid) {
        NationalRegistryResponse response = new NationalRegistryResponse();
        response.setValid(isValid);
        return response;
    }

    public static JudicialRegistryResponse buildJudicialRegistryResponse(boolean isValid) {
        JudicialRegistryResponse response = new JudicialRegistryResponse();
        response.setValid(isValid);
        return response;
    }

    public static InternalScoreResponse buldInternalScoreResponse(int score) {
        InternalScoreResponse response = new InternalScoreResponse();
        response.setScore(score);
        return response;
    }

    public static InternalScoreRequest buildInternalScoreRequest() {
        return new InternalScoreRequest(IDENTIFICATION_NUMBER);
    }

    public static JudicialRegistryRequest buildJudicialRegistryRequest() {
        return new JudicialRegistryRequest(IDENTIFICATION_NUMBER);
    }

    public static NationalRegistryRequest buildNationalRegistryRequest() {
        return new NationalRegistryRequest(buildPerson());
    }

    public static JudicialResponse buildJudicialResponse(boolean hasRecords) {
        return new JudicialResponse(hasRecords);
    }

    public static NationalResponse buildNationalResponse() {
        Person person = buildPerson();
        NationalResponse response =  new NationalResponse();
        response.setBirthDate(person.getBirthDate());
        response.setFirstName(person.getFirstName());
        response.setLastName(person.getLastName());
        response.setIdentificationNumber(person.getIdentificationNumber());
        return response;
    }

}
