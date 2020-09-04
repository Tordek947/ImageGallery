package net.atlassian.cmathtutor.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageData {

    private String id;
    private String author;
    private String camera;
    private String tags;
    @JsonAlias("cropped_picture")
    private String croppedPicture;
    @JsonAlias("full_picture")
    private String fullPicture;
}
