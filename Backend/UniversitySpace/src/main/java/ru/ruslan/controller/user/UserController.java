package ru.ruslan.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ruslan.service.user.UserService;

@Controller
public class UserController {
    @Autowired
    private final UserService userService;

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
        return ResponseEntity.ok().body(userService.findUserByUsername(username));
    }

    @GetMapping("/user")
    public String getUserPage() {
        return "html/user/userPage.html";
    }
}
