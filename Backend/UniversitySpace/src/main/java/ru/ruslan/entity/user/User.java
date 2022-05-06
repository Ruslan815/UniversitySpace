package ru.ruslan.entity.user;

import ru.ruslan.entity.chat.Chat;
import ru.ruslan.entity.chat.Message;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, message = "Не меньше 2 знаков")
    private String username;

    @Size(min = 2, message = "Не меньше 2 знаков")
    private String password;

    @Transient
    private String passwordConfirm;

    private Double balance = 1000D;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "chatMembers")
    private Set<Chat> availableChats = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "usersWhoDidNotRead")
    private List<Message> unreadMessages = new ArrayList<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Chat> getAvailableChats() {
        return availableChats;
    }

    public void setAvailableChats(Set<Chat> availableChats) {
        this.availableChats = availableChats;
    }

    public List<Message> getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(List<Message> unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
