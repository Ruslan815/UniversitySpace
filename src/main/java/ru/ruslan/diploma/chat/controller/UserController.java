package ru.ruslan.diploma.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ruslan.diploma.chat.error.ValidationResult;
import ru.ruslan.diploma.chat.model.User;
import ru.ruslan.diploma.chat.model.UserView;
import ru.ruslan.diploma.chat.service.UserService;
import ru.ruslan.diploma.chat.error.ErrorHandler;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> create(@RequestBody User user) {
        ValidationResult returnedRequestStatus = ErrorHandler.validateUser(user);
        if (returnedRequestStatus != ValidationResult.NO_ERROR) {
            return ResponseEntity.internalServerError().body(returnedRequestStatus);
        }
        return ResponseEntity.ok(userService.create(user));
    }

    @GetMapping("/users")
    public List<UserView> read() {
        return userService.getAllUserViews();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> get(@PathVariable(name = "userId") int userId) {
        UserView returnedUser;
        try {
            returnedUser = userService.getUserView(userId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_FOUND);
        }
        return ResponseEntity.ok(returnedUser);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<?> update(@PathVariable(name = "userId") int userId, @RequestBody User user) {
        ResponseEntity<?> response;
        ValidationResult returnedRequestStatus = ErrorHandler.validateUser(user);
        if (returnedRequestStatus == ValidationResult.NO_ERROR) {
            UserView returnedUser;
            try {
                returnedUser = userService.update(user, userId);
                response = ResponseEntity.ok(returnedUser);
            } catch (Exception e) {
                response = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_FOUND);
            }
        } else {
            response = ResponseEntity.internalServerError().body(returnedRequestStatus);
        }
        return response;
    }
}