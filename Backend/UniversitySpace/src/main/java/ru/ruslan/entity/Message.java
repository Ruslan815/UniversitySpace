package ru.ruslan.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer chatId;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String sendTime;

    @Column(nullable = false)
    private Long sendTimeSec;

    public Message(Integer messageId, Integer userId, Integer chatId, String text, String sendTime) {
        this.messageId = messageId;
        this.userId = userId;
        this.chatId = chatId;
        this.text = text;
        this.sendTime = sendTime;
    }

    public Message() {
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
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
        return Objects.equals(messageId, message.messageId) && Objects.equals(userId, message.userId) && Objects.equals(chatId, message.chatId) && Objects.equals(text, message.text) && Objects.equals(sendTime, message.sendTime) && Objects.equals(sendTimeSec, message.sendTimeSec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, userId, chatId, text, sendTime, sendTimeSec);
    }
}
