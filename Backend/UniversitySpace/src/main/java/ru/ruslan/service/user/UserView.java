package ru.ruslan.service.user;

import ru.ruslan.entity.user.User;

public class UserView {
    private String username;

    public UserView(User user) {
        this.username = user.getUsername();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
