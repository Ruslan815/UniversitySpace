package ru.ruslan.diploma.chat.model;

public class FeedMessage {

    String title;
    String link;
    String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "[title = " + title + ", link = " + link + ", author = " + author + "]";
    }

}
