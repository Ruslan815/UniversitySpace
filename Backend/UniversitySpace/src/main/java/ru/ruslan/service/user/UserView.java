package ru.ruslan.service.user;

public class UserView {
    private String username;

    public UserView(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
