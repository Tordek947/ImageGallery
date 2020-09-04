package net.atlassian.cmathtutor.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.atlassian.cmathtutor.converter.ImagePreviewDataToImageConverter;
import net.atlassian.cmathtutor.dto.PaginatedImagePreviewsData;
import net.atlassian.cmathtutor.entity.Image;
import net.atlassian.cmathtutor.repository.ImageRepository;
import net.atlassian.cmathtutor.service.ImageService;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultImageService implements ImageService {

    private static final int PAGE_SIZE = 10;
    private ImageRepository imageRepository;
    private ImagePreviewDataToImageConverter imageConverter;

    @Override
    public PaginatedImagePreviewsData getPagedImagePreviews(int pageNumber) {
        Page<Image> imagesPage = imageRepository.findAll(PageRequest.of(pageNumber, PAGE_SIZE));
        return PaginatedImagePreviewsData.builder()
                .hasMore(imagesPage.hasNext())
                .images(imagesPage.getContent().stream().map(imageConverter::convertToData)
                        .collect(Collectors.toList()))
                .page(pageNumber)
                .pageCount(imagesPage.getTotalPages())
                .build();
    }
}
