package ru.ruslan.entity;

import java.util.ArrayList;

public class ChatRoom {
    private Long id;
    private String roomName;
    private ArrayList<Long> memberIdList;
    private ArrayList<ChatMessage> history;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public ArrayList<Long> getMemberIdList() {
        return memberIdList;
    }

    public void setMemberIdList(ArrayList<Long> memberIdList) {
        this.memberIdList = memberIdList;
    }

    public ArrayList<ChatMessage> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<ChatMessage> history) {
        this.history = history;
    }
}
