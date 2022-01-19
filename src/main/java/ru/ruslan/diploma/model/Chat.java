package ru.ruslan.diploma.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatId;

    private String name;

    private String rssLink;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> chatMembers = new HashSet<>();

    public Chat(Integer chatId, String name) {
        this.chatId = chatId;
        this.name = name;
    }

    public Chat() {
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

    public Set<User> getChatMembers() {
        return chatMembers;
    }

    public void setChatMembers(Set<User> chatMembers) {
        this.chatMembers = chatMembers;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "chatId=" + chatId +
                ", name='" + name + '\'' +
                ", chatMembers=" + chatMembers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(chatId, chat.chatId) && Objects.equals(name, chat.name) && Objects.equals(chatMembers, chat.chatMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, name, chatMembers);
    }
}
