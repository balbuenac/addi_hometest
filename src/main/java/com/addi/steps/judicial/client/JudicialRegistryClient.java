package com.addi.steps.judicial.client;

import com.addi.common.feign.FeignClientConfiguration;
import com.addi.steps.judicial.client.request.JudicialRequest;
import com.addi.steps.judicial.client.response.JudicialResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "judicialRegistry", url = "${app.judicial.url}", configuration = {FeignClientConfiguration.class})
public interface JudicialRegistryClient {

    @RequestMapping(method = RequestMethod.GET, value = "/verify")
    JudicialResponse verify(JudicialRequest request);

}
