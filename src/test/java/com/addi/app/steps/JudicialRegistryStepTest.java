package com.addi.app.steps;

import com.addi.app.Generator;
import com.addi.common.exceptions.LeadException;
import com.addi.steps.judicial.JudicialRegistryStep;
import com.addi.steps.judicial.client.JudicialRegistryClient;
import com.addi.steps.judicial.response.JudicialRegistryResponse;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class JudicialRegistryStepTest {

    private JudicialRegistryClient client = Mockito.mock(JudicialRegistryClient.class);
    private JudicialRegistryStep step = new JudicialRegistryStep(client);

    @AfterMethod
    private void setup() {
        Mockito.reset(client);
    }

    @Test
    public void test_PersonHasNoJudicialRecords() {
        Mockito.when(client.verify(Mockito.any())).thenReturn(Generator.buildJudicialResponse(true));
        JudicialRegistryResponse response = step.evaluate(Generator.buildJudicialRegistryRequest());
        Assert.assertEquals(response.isValid(), true);
    }

    @Test
    public void test_PersonHasJudicialRecords() {
        Mockito.when(client.verify(Mockito.any())).thenReturn(Generator.buildJudicialResponse(false));
        JudicialRegistryResponse response = step.evaluate(Generator.buildJudicialRegistryRequest());
        Assert.assertEquals(response.isValid(), false);
    }

    @Test(expectedExceptions = LeadException.class)
    public void test_whenJudicialSystemIsDown() {
        Mockito.when(client.verify(Mockito.any())).thenThrow(new LeadException());
        step.evaluate(Generator.buildJudicialRegistryRequest());
    }

}
