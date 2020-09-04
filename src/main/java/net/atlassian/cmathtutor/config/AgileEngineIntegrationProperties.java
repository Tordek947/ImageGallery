package net.atlassian.cmathtutor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Component
public class AgileEngineIntegrationProperties {

    @Value("${integration.agileengine.baseurl}")
    private String baseUrl;
    @Value("${integration.agileengine.apikey}")
    private String apiKey;
    @Value("${integration.agileengine.image-baseurl}")
    private String imageBaseUrl;
}
