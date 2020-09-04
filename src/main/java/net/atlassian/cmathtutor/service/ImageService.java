package net.atlassian.cmathtutor.service;

import net.atlassian.cmathtutor.dto.PaginatedImagePreviewsData;

public interface ImageService {

    PaginatedImagePreviewsData getPagedImagePreviews(int pageNumber);
}
