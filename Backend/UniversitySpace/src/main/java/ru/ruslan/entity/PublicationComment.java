package ru.ruslan.entity;

import javax.persistence.Entity;

@Entity(name = "publication_comments")
public class PublicationComment extends Comment {
    private Long publicationId;

    public PublicationComment() {
    }

    public PublicationComment(Long commentId, Long authorId, String creationTime, String text, Long publicationId) {
        super(commentId, authorId, creationTime, text);
        this.publicationId = publicationId;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }
}
