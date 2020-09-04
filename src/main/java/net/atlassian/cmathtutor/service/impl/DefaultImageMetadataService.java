package net.atlassian.cmathtutor.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.atlassian.cmathtutor.converter.ImageDataToImageMetadataConverter;
import net.atlassian.cmathtutor.dto.ImageData;
import net.atlassian.cmathtutor.entity.ImageMetadata;
import net.atlassian.cmathtutor.exception.EntityNotFoundException;
import net.atlassian.cmathtutor.repository.ImageMetadataRepository;
import net.atlassian.cmathtutor.service.ImageMetadataService;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultImageMetadataService implements ImageMetadataService {

    private ImageMetadataRepository imageMetadataRepository;
    private ImageDataToImageMetadataConverter imageMetadataConverter;

    @Transactional
    @Override
    public void updateAllImages(Map<String, ImageData> imagesById) {
        List<ImageMetadata> imagesToDelete = new LinkedList<>();
        Set<String> imageIdsToCreate = imagesById.keySet().stream()
                .collect(Collectors.toSet());

        for (ImageMetadata imageMetadata : imageMetadataRepository.findAll()) {
            String currentImageId = imageMetadata.getImage().getId();
            ImageData imageData = imagesById.get(currentImageId);
            if (imageData == null) {
                imagesToDelete.add(imageMetadata);
            } else {
                imageMetadataConverter.populate(imageData, imageMetadata);
                imageIdsToCreate.remove(currentImageId);
            }
        }

        List<ImageMetadata> imagesToCreate = imageIdsToCreate.stream()
                .map(imagesById::get)
                .map(imageMetadataConverter::convert)
                .collect(Collectors.toList());
        imageMetadataRepository.deleteAll(imagesToDelete);
        imageMetadataRepository.saveAll(imagesToCreate);
    }

    @Override
    public ImageData getImageById(String id) throws EntityNotFoundException {
        ImageMetadata imageMetadata = imageMetadataRepository.findByImageId(id)
                .orElseThrow(EntityNotFoundException::new);
        return imageMetadataConverter.convertToData(imageMetadata);
    }

    @Override
    public List<ImageData> getAllWithAnyMetafieldMatched(String token) {
        return imageMetadataRepository.findByAuthorOrCameraOrHashTagsContainsToken(token).stream()
                .map(imageMetadataConverter::convertToData)
                .collect(Collectors.toList());
    }
}
