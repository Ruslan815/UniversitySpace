package ru.ruslan.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ruslan.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String getAdminPanel() {
        return "html/user/adminPanel";
    }

    @PostMapping("/admin/user/give/admin")
    public ResponseEntity<?> giveAdminRoleById(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok().body(userService.giveAdminRoleById(userId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/admin/user/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId) {
        return ResponseEntity.ok().body(userService.deleteUser(userId));
    }
}
