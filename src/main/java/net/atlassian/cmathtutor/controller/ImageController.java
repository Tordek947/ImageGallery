package net.atlassian.cmathtutor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.atlassian.cmathtutor.dto.ImageData;
import net.atlassian.cmathtutor.dto.PaginatedImagePreviewsData;
import net.atlassian.cmathtutor.exception.EntityNotFoundException;
import net.atlassian.cmathtutor.service.ImageMetadataService;
import net.atlassian.cmathtutor.service.ImageService;

@Tag(name = "image", description = "Image metadata actions")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(path = "/images")
public class ImageController {

    private ImageService imageService;
    private ImageMetadataService imageMetadataService;

    @Operation(summary = "Get paged image preview metadata for specified page")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PaginatedImagePreviewsData getPagedImages(
            @RequestParam(name = "page", required = false, defaultValue = "1") int pageNumber
    ) {
        return imageService.getPagedImagePreviews(pageNumber);
    }

    @Operation(summary = "Get detailed image metadata by image id")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "Not found",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ImageData getPagedImages(@PathVariable("id") String imageId) throws EntityNotFoundException {
        return imageMetadataService.getImageById(imageId);
    }
}
