package ru.ruslan.diploma.chat.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class AttachRequest {
    private Integer userId;
    private Integer chatId;
    private MultipartFile file;

    public AttachRequest(Integer userId, Integer chatId, MultipartFile file) {
        this.userId = userId;
        this.chatId = chatId;
        this.file = file;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachRequest that = (AttachRequest) o;
        return Objects.equals(userId, that.userId) && Objects.equals(chatId, that.chatId) && Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, chatId, file);
    }

    @Override
    public String toString() {
        return "AttachRequest{" +
                "userId=" + userId +
                ", chatId=" + chatId +
                ", file=" + file +
                '}';
    }
}
