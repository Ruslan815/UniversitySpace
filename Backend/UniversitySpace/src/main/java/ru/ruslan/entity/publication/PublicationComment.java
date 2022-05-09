package ru.ruslan.entity.publication;

import ru.ruslan.entity.abstracted.Comment;

import javax.persistence.Entity;

@Entity(name = "publication_comments")
public class PublicationComment extends Comment {
    private Long publicationId;
    private String authorUsername;

    public PublicationComment() {
    }

    public PublicationComment(Long commentId, Long authorId, String creationTime, String text, Long publicationId, String authorUsername) {
        super(commentId, authorId, creationTime, text);
        this.publicationId = publicationId;
        this.authorUsername = authorUsername;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}
