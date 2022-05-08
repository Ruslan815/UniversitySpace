package ru.ruslan.service.user;

import ru.ruslan.entity.user.User;

public class UserView {
    private String username;
    private Long solvedTaskCount;

    public UserView(User user) {
        this.username = user.getUsername();
        this.solvedTaskCount = user.getSolvedTaskCount();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getSolvedTaskCount() {
        return solvedTaskCount;
    }

    public void setSolvedTaskCount(Long solvedTaskCount) {
        this.solvedTaskCount = solvedTaskCount;
    }
}
