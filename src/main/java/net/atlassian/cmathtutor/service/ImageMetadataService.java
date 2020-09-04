package net.atlassian.cmathtutor.service;

import java.util.List;
import java.util.Map;

import net.atlassian.cmathtutor.dto.ImageData;
import net.atlassian.cmathtutor.exception.EntityNotFoundException;

public interface ImageMetadataService {

    void updateAllImages(Map<String, ImageData> imagesById);

    ImageData getImageById(String id) throws EntityNotFoundException;

    List<ImageData> getAllWithAnyMetafieldMatched(String token);
}
