package net.atlassian.cmathtutor.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "image_metadata")
@Data
public class ImageMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK")
    private Long pk;

    @Column(name = "author")
    private String author;

    @Column(name = "camera")
    private String camera;

    @Column(name = "full_picture_url", nullable = false)
    private String fullPictureUrl;

    @Column(name = "hash_tags")
    private String hashTags;

    @OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
        fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "image_PK", nullable = false, unique = true)
    private Image image;
}
