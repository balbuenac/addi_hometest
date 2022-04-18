package com.addi.common.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeignErrorDecoder implements ErrorDecoder {

    private Logger LOG = LoggerFactory.getLogger(FeignErrorDecoder.class);

    @Override
    public Exception decode(String s, Response response) {
        LOG.info("FeignErrorDecoder::decode Exception was produced when response [{}]", response);
        return null;
    }

}
