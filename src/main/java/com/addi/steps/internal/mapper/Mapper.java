package com.addi.steps.internal.mapper;

import com.addi.steps.internal.response.InternalScoreResponse;

public class Mapper {
    public static InternalScoreResponse buildResponse(int score) {
        return new InternalScoreResponse(score);
    }
}
