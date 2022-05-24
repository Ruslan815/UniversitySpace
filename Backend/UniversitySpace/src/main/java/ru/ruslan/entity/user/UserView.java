package ru.ruslan.entity.user;

import java.util.Arrays;

public class UserView {
    private String username;
    private Long solvedTaskCount;
    private String roles;

    public UserView(User user) {
        this.username = user.getUsername();
        this.solvedTaskCount = user.getSolvedTaskCount();
        this.roles = Arrays.toString(user.getRoles().toArray());
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
