package ru.ruslan.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ruslan.entity.user.SecurityUser;
import ru.ruslan.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private SessionRegistry sessionRegistry;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(userService.allUsers());
    }

    @GetMapping("/users")
    public String getUsersPage() {
        return "html/user/allUsersPage.html";
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUser(@RequestParam String username) {
        return ResponseEntity.ok().body(userService.getUserViewByUsername(username));
    }

    @GetMapping("/api/user/idByUsername")
    public ResponseEntity<?> getUserIdByUsername(@RequestParam String username) {
        Long id = userService.getUserIdByUsername(username);
        if (id == null) return ResponseEntity.status(500).body("Not found user with username: " + username);
        return ResponseEntity.ok().body(id);
    }

    @GetMapping("/user")
    public String getUserPage() {
        return "html/user/userPage.html";
    }

    @GetMapping("/api/user/online")
    public ResponseEntity<?> isUserOnline(@RequestParam String username) {
        final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        for (final Object principal : allPrincipals) {
            if (principal instanceof SecurityUser && ((SecurityUser) principal).getUsername().equals(username)) {
                List<SessionInformation> activeUserSessions = sessionRegistry.getAllSessions(principal, false); // includeExpiredSessions
                if (!activeUserSessions.isEmpty()) {
                    return ResponseEntity.ok().body(String.valueOf(true));
                }
            }
        }

        return ResponseEntity.ok().body(String.valueOf(false));
    }

    @GetMapping("/api/users/online")
    public ResponseEntity<?> getOnlineUsernamesList() {
        List<String> usernameList = new ArrayList<>();
        final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();

        for (final Object principal : allPrincipals) {
            if (principal instanceof SecurityUser) {
                final SecurityUser user = (SecurityUser) principal;
                List<SessionInformation> activeUserSessions = sessionRegistry.getAllSessions(principal, false); // includeExpiredSessions
                if (!activeUserSessions.isEmpty()) {
                    usernameList.add(user.getUsername());
                }
            }
        }

        return ResponseEntity.ok().body(usernameList);
    }

    @GetMapping("/api/user/chats")
    public ResponseEntity<?> getAvailableChatsByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok().body(userService.getAvailableChatsByUserId(userId));
    }
}
