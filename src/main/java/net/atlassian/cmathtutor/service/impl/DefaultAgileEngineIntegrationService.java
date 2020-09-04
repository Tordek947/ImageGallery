package net.atlassian.cmathtutor.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.atlassian.cmathtutor.config.AgileEngineIntegrationProperties;
import net.atlassian.cmathtutor.dto.AgileEngineAuthRequestData;
import net.atlassian.cmathtutor.dto.AgileEngineAuthResponseData;
import net.atlassian.cmathtutor.dto.ImageData;
import net.atlassian.cmathtutor.dto.PaginatedImagePreviewsData;
import net.atlassian.cmathtutor.exception.IntegrationException;
import net.atlassian.cmathtutor.exception.IntegrationException.ErrorType;
import net.atlassian.cmathtutor.service.AgileEngineIntegrationService;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class DefaultAgileEngineIntegrationService implements AgileEngineIntegrationService {

    private static final String AUTH_URL = "/auth";
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE_PATTERN = "Bearer %s";

    private static final String GET_IMAGES_PAGE_URL_PATTERN = "/images?page=%s";
    private static final String GET_SPECIFIC_IMAGE_PAGE_URL_PATTERN = "/images/%s";

    private WebClient agileEngineWebClient;
    private AgileEngineIntegrationProperties agileEngineIntegrationProperties;

    @Override
    public Map<String, ImageData> getAllImagesData() {
        String authToken = authenticate().getToken();
        Map<String, ImageData> imagesById = new HashMap<>();
        int currentPage = 1;
        PaginatedImagePreviewsData pagePreviewsData;
        do {
            log.debug("Loading images, page {}", currentPage);
            pagePreviewsData = getPaginatedImagePreviewsByAuthTokenAndPage(authToken, currentPage);

            pagePreviewsData.getImages().stream()
                    .map(imagePreview -> getImageDataByTokenAndImageId(authToken, imagePreview.getId()))
                    .forEach(image -> imagesById.put(image.getId(), image));
            currentPage++;
        } while (Boolean.TRUE.equals(pagePreviewsData.getHasMore())
                && pagePreviewsData.getPage() < pagePreviewsData.getPageCount());
        return imagesById;
    }

    private AgileEngineAuthResponseData authenticate() {
        String agileEngineApiKey = agileEngineIntegrationProperties.getApiKey();
        log.debug("Authenticating with key {}", agileEngineApiKey);
        AgileEngineAuthRequestData authRequestBody = AgileEngineAuthRequestData.builder()
                .apiKey(agileEngineApiKey)
                .build();

        ResponseSpec responseSpec = agileEngineWebClient.post()
                .uri(AUTH_URL)
                .body(Mono.just(authRequestBody), AgileEngineAuthRequestData.class)
                .retrieve();
        AgileEngineAuthResponseData authResponseData = addExceptionHandlers(responseSpec)
                .bodyToMono(AgileEngineAuthResponseData.class)
                .block();
        log.info("Received authentication data: {}", authResponseData);
        return authResponseData;
    }

    private PaginatedImagePreviewsData getPaginatedImagePreviewsByAuthTokenAndPage(String authToken, int currentPage) {
        ResponseSpec responseSpec = agileEngineWebClient.get()
                .uri(String.format(GET_IMAGES_PAGE_URL_PATTERN, currentPage))
                .header(AUTHORIZATION_HEADER_KEY, String.format(AUTHORIZATION_HEADER_VALUE_PATTERN, authToken))
                .retrieve();
        return addExceptionHandlers(responseSpec)
                .bodyToMono(PaginatedImagePreviewsData.class)
                .block();
    }

    private ImageData getImageDataByTokenAndImageId(String authToken, String imageId) {
        log.debug("Loading full image data for id {} and token {}", imageId, authToken);
        ResponseSpec responseSpec = agileEngineWebClient.get()
                .uri(String.format(GET_SPECIFIC_IMAGE_PAGE_URL_PATTERN, imageId))
                .header(AUTHORIZATION_HEADER_KEY, String.format(AUTHORIZATION_HEADER_VALUE_PATTERN, authToken))
                .retrieve();
        return addExceptionHandlers(responseSpec)
                .bodyToMono(ImageData.class)
                .block();
    }
    
    private ResponseSpec addExceptionHandlers(ResponseSpec responseSpec) {
        return responseSpec
                .onStatus(HttpStatus::is5xxServerError,
                        response -> Mono.error(new IntegrationException(ErrorType.INTERNAL_SERVER_ERROR)))
                .onStatus(HttpStatus::is4xxClientError,
                        response -> Mono.error(new IntegrationException(ErrorType.CLIENT_ERROR)));
    }
}
