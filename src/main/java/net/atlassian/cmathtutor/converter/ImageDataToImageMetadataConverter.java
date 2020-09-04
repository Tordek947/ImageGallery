package net.atlassian.cmathtutor.converter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import net.atlassian.cmathtutor.dto.ImageData;
import net.atlassian.cmathtutor.entity.Image;
import net.atlassian.cmathtutor.entity.ImageMetadata;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ImageDataToImageMetadataConverter implements Converter<ImageData, ImageMetadata> {

    private static final String EMPTY_STRING = "";
    private static final String SPACE = " ";
    private static final String NOT_FIRST_HASH_SIGN_REGEX = "(?<=.+)[#]";
    private static final String SPACE_HASH = SPACE + "#";

    private ImageUrlConverter imageUrlConverter;

    @Override
    public ImageMetadata convert(ImageData source) {
        ImageMetadata target = new ImageMetadata();
        populate(source, target);
        return target;
    }

    public void populate(ImageData source, ImageMetadata target) {
        target.setAuthor(source.getAuthor());
        target.setCamera(source.getCamera());
        target.setFullPictureUrl(imageUrlConverter.convert(source.getFullPicture()));
        target.setHashTags(convertHashTagsDataToHashTags(source.getTags()));
        Image image = Optional.ofNullable(target.getImage()).orElse(new Image());
        image.setCroppedPictureUrl(imageUrlConverter.convert(source.getCroppedPicture()));
        image.setId(source.getId());
        target.setImage(image);
    }

    @Override
    public ImageData convertToData(ImageMetadata source) {
        return ImageData.builder()
                .author(source.getAuthor())
                .camera(source.getCamera())
                .croppedPicture(imageUrlConverter.convertToData(source.getImage().getCroppedPictureUrl()))
                .fullPicture(imageUrlConverter.convertToData(source.getFullPictureUrl()))
                .id(source.getImage().getId())
                .tags(convertHashTagsToHashTagsData(source.getHashTags()))
                .build();
    }

    private String convertHashTagsDataToHashTags(String hashTagsData) {
        return hashTagsData.replaceAll(SPACE, EMPTY_STRING);
    }

    private String convertHashTagsToHashTagsData(String hashTags) {
        return hashTags.replaceAll(NOT_FIRST_HASH_SIGN_REGEX, SPACE_HASH);
    }
}
