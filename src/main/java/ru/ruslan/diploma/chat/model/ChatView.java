package ru.ruslan.diploma.chat.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatView {
    private Integer chatId;

    private String name;

    private String rssLink;

    public ChatView(Chat chat) {
        this.setChatId(chat.getChatId());
        this.setName(chat.getName());
        this.setRssLink(chat.getRssLink());
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

    public String getRssLink() {
        return rssLink;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
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
