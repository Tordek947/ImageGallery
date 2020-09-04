package net.atlassian.cmathtutor.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginatedImagePreviewsData {

    @JsonAlias("pictures")
    private List<ImagePreviewData> images;
    private Integer page;
    private Integer pageCount;
    private Boolean hasMore;
}
