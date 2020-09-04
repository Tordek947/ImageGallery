package net.atlassian.cmathtutor.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import net.atlassian.cmathtutor.entity.Image;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<Image, Long> {

}
