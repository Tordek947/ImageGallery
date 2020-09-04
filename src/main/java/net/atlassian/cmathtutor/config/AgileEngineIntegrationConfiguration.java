package net.atlassian.cmathtutor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AgileEngineIntegrationConfiguration {

    @Autowired
    private AgileEngineIntegrationProperties agileEngineIntegrationProperties;

    @Bean
    public WebClient agileEngineWebClient() {
        return WebClient.builder()
                .baseUrl(agileEngineIntegrationProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
