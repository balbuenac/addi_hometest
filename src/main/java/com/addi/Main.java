package com.addi;

import com.addi.app.LeadEvaluator;
import com.addi.common.request.LeadRequest;
import com.addi.common.response.LeadResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

@SpringBootApplication
@EnableFeignClients({"com.addi.steps.national.client", "com.addi.steps.judicial.client"})
@ComponentScan(
        "com.addi"
)
public class Main implements CommandLineRunner {

    private Logger LOG = LoggerFactory.getLogger(Main.class);

    @Autowired
    private LeadEvaluator evaluator;

    public static void main (String [ ] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info(":::Starting Application:::");
        ObjectMapper mapper = new ObjectMapper();

        try {
            File file = new ClassPathResource("leads/leads.json").getFile();
            LeadRequest lead = mapper.readValue(file, LeadRequest.class);
            LOG.info(":::Processing Request:  [{}]", lead);
            LeadResponse response = evaluator.evaluate(lead);
            LOG.info(":::Request processed with RESULT:  [{}]", response);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(":::Error in Application:::[{}] StackTrace: [{}]", e.getMessage(), e.getStackTrace());
        }
    }
}
