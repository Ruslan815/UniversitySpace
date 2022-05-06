package ru.ruslan.entity.chat;

public class ChatMember {
    Integer userId;
    Integer chatId;

    public ChatMember(Integer userId, Integer chatId) {
        this.userId = userId;
        this.chatId = chatId;
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

    @Override
    public String toString() {
        return "ChatMember{" +
                "userId=" + userId +
                ", chatId=" + chatId +
                '}';
    }
}
