package net.atlassian.cmathtutor.service;

import java.util.Map;

import net.atlassian.cmathtutor.dto.ImageData;

public interface AgileEngineIntegrationService {
    
    Map<String, ImageData> getAllImagesData();
}
