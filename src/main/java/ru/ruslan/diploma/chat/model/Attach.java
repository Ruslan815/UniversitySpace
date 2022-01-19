package ru.ruslan.diploma.chat.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity(name = "Attachments")
public class Attach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    private Integer chatId;

    @Column(nullable = false)
    @Lob
    private byte[] file;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String contentType;

    public Attach(Integer userId, Integer chatId, byte[] file, String fileName, String contentType) {
        this.userId = userId;
        this.chatId = chatId;
        this.file = file;
        this.fileName = fileName;
        this.contentType = contentType;
    }

    public Attach() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "Attach{" +
                "id=" + id +
                ", userId=" + userId +
                ", chatId=" + chatId +
                ", file=" + Arrays.toString(file) +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attach attach = (Attach) o;
        return Objects.equals(id, attach.id) && Objects.equals(userId, attach.userId) && Objects.equals(chatId, attach.chatId) && Arrays.equals(file, attach.file) && Objects.equals(fileName, attach.fileName) && Objects.equals(contentType, attach.contentType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, userId, chatId, fileName, contentType);
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }
}
