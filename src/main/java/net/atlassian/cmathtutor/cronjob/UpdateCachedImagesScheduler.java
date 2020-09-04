package net.atlassian.cmathtutor.cronjob;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.atlassian.cmathtutor.dto.ImageData;
import net.atlassian.cmathtutor.service.AgileEngineIntegrationService;
import net.atlassian.cmathtutor.service.ImageMetadataService;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateCachedImagesScheduler {

    private AgileEngineIntegrationService agileEngineIntegrationService;
    private ImageMetadataService imageMetadataService;

    @Scheduled(cron = "${cronjob.image.metadata.refresh-cron}")
    public void updateCachedImagesMetadata() {
        log.info("updateCachedImagesMetadata cronJob started");
        Map<String, ImageData> allImagesData = agileEngineIntegrationService.getAllImagesData();
        log.info("updateCachedImagesMetadata cronJob fetched {} images", allImagesData.size());
        imageMetadataService.updateAllImages(allImagesData);
        log.info("updateCachedImagesMetadata cronJob finished successfully");
    }
}
