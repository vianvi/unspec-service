package uk.gov.hmcts.reform.unspec;

import org.camunda.bpm.extension.rest.EnableCamundaRestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCamundaRestClient
@EnableCircuitBreaker
@EnableFeignClients(basePackages = {"uk.gov.hmcts.reform.idam.client", "uk.gov.hmcts.reform.unspec"})
@SuppressWarnings("HideUtilityClassConstructor") // Spring needs a constructor, its not a utility class
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
