package ru.ruslan.entity.chat;

import ru.ruslan.entity.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long chatId;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String sendTime;

    @Column(nullable = false)
    private Long sendTimeSec;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> usersWhoDidNotRead = new ArrayList<>();

    public Message(Long messageId, Long userId, Long chatId, String text, String sendTime) {
        this.messageId = messageId;
        this.userId = userId;
        this.chatId = chatId;
        this.text = text;
        this.sendTime = sendTime;
    }

    public Message() {
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Long getSendTimeSec() {
        return sendTimeSec;
    }

    public void setSendTimeSec(Long sendTimeSec) {
        this.sendTimeSec = sendTimeSec;
    }

    public List<User> getUsersWhoDidNotRead() {
        return usersWhoDidNotRead;
    }

    public void setUsersWhoDidNotRead(List<User> usersWhoDidNotRead) {
        this.usersWhoDidNotRead = usersWhoDidNotRead;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", userId=" + userId +
                ", chatId=" + chatId +
                ", text='" + text + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", sendTimeInMillis=" + sendTimeSec +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(messageId, message.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId);
    }
}
