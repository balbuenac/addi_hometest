package com.addi.steps.internal;

import com.addi.steps.internal.mapper.Mapper;
import com.addi.steps.internal.request.InternalScoreRequest;
import com.addi.steps.internal.response.InternalScoreResponse;
import org.springframework.stereotype.Service;

@Service
public class InternalScoreStep {

    public static int MIN_SCORE = 0;
    public static int MAX_SCORE = 100;

    // TODO: Add connection to internal system using HTTP ?
    public InternalScoreResponse evaluate(InternalScoreRequest request) {

        int factor = (MAX_SCORE - MIN_SCORE +1) + MIN_SCORE;
        int randomScore = (int)Math.floor(Math.random()*factor);
        return Mapper.buildResponse(randomScore);
    }
}
