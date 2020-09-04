package net.atlassian.cmathtutor.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import net.atlassian.cmathtutor.config.AgileEngineIntegrationProperties;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ImageUrlConverter {

    private static final String EMPTY_STRING = "";

    private AgileEngineIntegrationProperties agileEngineIntegrationProperties;

    public String convert(String fullImageUrl) {
        return fullImageUrl.replaceFirst(agileEngineIntegrationProperties.getImageBaseUrl(), EMPTY_STRING);
    }

    public String convertToData(String reducedImageUrl) {
        return agileEngineIntegrationProperties.getImageBaseUrl() + reducedImageUrl;
    }
}
