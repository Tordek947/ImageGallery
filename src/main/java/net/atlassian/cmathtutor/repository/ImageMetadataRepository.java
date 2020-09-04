package net.atlassian.cmathtutor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.atlassian.cmathtutor.entity.ImageMetadata;

@Repository
public interface ImageMetadataRepository extends CrudRepository<ImageMetadata, Long> {

    @Query(value = "select i from ImageMetadata i left join fetch i.image where i.image.id = :id")
    Optional<ImageMetadata> findByImageId(@Param("id") String imageId);

    @Query(value = "select i from ImageMetadata i left join fetch i.image where "
            + "i.author LIKE %:token% OR i.camera LIKE %:token% OR i.hashTags LIKE %:token%")
    List<ImageMetadata> findByAuthorOrCameraOrHashTagsContainsToken(@Param("token") String token);
}
