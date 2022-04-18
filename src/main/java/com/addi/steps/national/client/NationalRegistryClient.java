package com.addi.steps.national.client;

import com.addi.common.feign.FeignClientConfiguration;
import com.addi.steps.national.client.request.NationalRequest;
import com.addi.steps.national.client.response.NationalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "nationalRegistry", url = "${app.national.url}", configuration = {FeignClientConfiguration.class})
public interface NationalRegistryClient {

    @RequestMapping(method = RequestMethod.GET, value = "/verify")
    NationalResponse verify(NationalRequest request);

}
