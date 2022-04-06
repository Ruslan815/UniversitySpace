package ru.ruslan.entity;

import java.util.Objects;

public class ChatView {
    private Integer chatId;
    private String name;

    public ChatView(Chat chat) {
        this.setChatId(chat.getChatId());
        this.setName(chat.getName());
    }

    public ChatView(Integer chatId, String name) {
        this.chatId = chatId;
        this.name = name;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChatView{" +
                "chatId=" + chatId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatView chatView = (ChatView) o;
        return Objects.equals(chatId, chatView.chatId) && Objects.equals(name, chatView.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, name);
    }
}
