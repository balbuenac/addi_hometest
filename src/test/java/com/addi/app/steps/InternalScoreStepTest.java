package com.addi.app.steps;

import com.addi.app.Generator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.addi.steps.internal.InternalScoreStep;
import com.addi.steps.internal.response.InternalScoreResponse;

@Test
public class InternalScoreStepTest {

    private InternalScoreStep step;

    @BeforeClass
    private void setup() {
        this.step = new InternalScoreStep();
    }

    public void testScoreBelongsToValidRange_Success() {
        InternalScoreResponse response = this.step.evaluate(Generator.buildInternalScoreRequest());
        Assert.assertTrue((response.getScore() >= InternalScoreStep.MIN_SCORE && response.getScore() <= InternalScoreStep.MAX_SCORE));
    }

}
