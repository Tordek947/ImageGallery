package net.atlassian.cmathtutor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.atlassian.cmathtutor.dto.ImageData;
import net.atlassian.cmathtutor.service.ImageMetadataService;

@Tag(name = "image search", description = "Image search API")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(path = "/search")
public class ImageSearchController {

    private ImageMetadataService imageMetadataService;

    @Operation(summary = "Get all image metadata, where either camera, author or hashtags contains token")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping(path = "/{searchTerm}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ImageData> getAllImageMetadataWhichMatchesToken(@PathVariable("searchTerm") String token) {
        return imageMetadataService.getAllWithAnyMetafieldMatched(token);
    }
}
