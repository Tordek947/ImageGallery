package net.atlassian.cmathtutor.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import net.atlassian.cmathtutor.dto.ImagePreviewData;
import net.atlassian.cmathtutor.entity.Image;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ImagePreviewDataToImageConverter implements Converter<ImagePreviewData, Image> {

    private ImageUrlConverter imageUrlConverter;

    @Override
    public Image convert(ImagePreviewData data) {
        return Image.builder()
                .croppedPictureUrl(imageUrlConverter.convert(data.getCroppedPictureUrl()))
                .id(data.getId())
                .build();
    }

    @Override
    public ImagePreviewData convertToData(Image entity) {
        return ImagePreviewData.builder()
                .croppedPictureUrl(imageUrlConverter.convertToData(entity.getCroppedPictureUrl()))
                .id(entity.getId())
                .build();
    }
}
