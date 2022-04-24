package ru.ruslan.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity(name = "publications")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publicationId;
    private String title;
    private String content;
    private Long authorId;
    private String date;

    public Publication() {
    }

    public Publication(Long publicationId, String title, String content, Long authorId, String date) {
        this.publicationId = publicationId;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.date = date;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthor() {
        return authorId;
    }

    public void setAuthor(Long authorId) {
        this.authorId = authorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + publicationId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", authorId=" + authorId +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return Objects.equals(publicationId, that.publicationId) && Objects.equals(title, that.title) && Objects.equals(content, that.content)
                && Objects.equals(authorId, that.authorId) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicationId, title, content, authorId, date);
    }
}
