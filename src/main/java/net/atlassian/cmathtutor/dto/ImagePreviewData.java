package net.atlassian.cmathtutor.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImagePreviewData {

    private String id;
    @JsonAlias("cropped_picture")
    private String croppedPictureUrl;
}
