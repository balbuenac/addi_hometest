package com.addi.app.steps;

import com.addi.app.Generator;
import com.addi.common.exceptions.LeadException;
import com.addi.steps.national.NationalRegistryStep;
import com.addi.steps.national.client.NationalRegistryClient;
import com.addi.steps.national.response.NationalRegistryResponse;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class NationalRegistryStepTest {

    private NationalRegistryClient client = Mockito.mock(NationalRegistryClient.class);
    private NationalRegistryStep step = new NationalRegistryStep(client);

    @AfterMethod
    private void setup() {
        Mockito.reset(client);
    }

    @Test
    public void test_PersonHasNoNationalRecords() {
        Mockito.when(client.verify(Mockito.any())).thenReturn(Generator.buildNationalResponse());
        NationalRegistryResponse response = step.evaluate(Generator.buildNationalRegistryRequest());
        Assert.assertEquals(response.isValid(), true);
    }

    @Test
    public void test_PersonHasNationalRecords() {
        Mockito.when(client.verify(Mockito.any())).thenReturn(null);
        NationalRegistryResponse response = step.evaluate(Generator.buildNationalRegistryRequest());
        Assert.assertEquals(response.isValid(), false);
    }

    @Test(expectedExceptions = LeadException.class)
    public void test_whenNationalSystemIsDown() {
        Mockito.when(client.verify(Mockito.any())).thenThrow(new LeadException());
        step.evaluate(Generator.buildNationalRegistryRequest());
    }

}
