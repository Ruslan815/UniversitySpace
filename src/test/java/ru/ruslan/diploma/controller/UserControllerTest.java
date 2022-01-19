package ru.ruslan.diploma.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import ru.ruslan.diploma.error.ValidationResult;
import ru.ruslan.diploma.model.User;
import ru.ruslan.diploma.model.UserView;
import ru.ruslan.diploma.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    void createSuccessful() {
        User passedUser = new User(null, "cat", "dog");
        UserView expectedUser = new UserView(1, "cat", "dog");
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(expectedUser);
        Mockito.when(userService.create(passedUser)).thenReturn(expectedUser);
        ResponseEntity<?> actualResponse = userController.create(passedUser);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createFailedFirstnameNotFound() {
        User passedUser = new User(null, null, "dog");
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.FIRSTNAME_NOT_FOUND);
        ResponseEntity<?> actualResponse = userController.create(passedUser);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createFailedLastnameNotFound() {
        User passedUser = new User(null, "cat", null);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.LASTNAME_NOT_FOUND);
        ResponseEntity<?> actualResponse = userController.create(passedUser);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createFailedFirstnameAndLastnameNotFound() {
        User passedUser = new User(null, null, null);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.FIRSTNAME_AND_LASTNAME_NOT_FOUND);
        ResponseEntity<?> actualResponse = userController.create(passedUser);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void readSuccessful() {
        List<UserView> expectedList = new ArrayList<>();
        expectedList.add(new UserView(1, "Fil", "cat"));
        expectedList.add(new UserView(2, "Emma", "cat"));
        expectedList.add(new UserView(3, "Dymok", "cat"));

        Mockito.when(userService.getAllUserViews()).thenReturn(expectedList);
        List<UserView> actualList = userController.read();
        assertEquals(expectedList, actualList);
    }

    @Test
    void readSuccessfulEmptyList() {
        List<UserView> expectedList = new ArrayList<>();
        Mockito.when(userService.getAllUserViews()).thenReturn(expectedList);
        List<UserView> actualList = userController.read();
        assertEquals(expectedList, actualList);
    }

    @Test
    void getSuccessful() {
        int userId = 42;
        UserView expectedUser = new UserView(userId, "cat", "dog");
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(expectedUser);
        Mockito.when(userService.getUserView(userId)).thenReturn(expectedUser);
        ResponseEntity<?> actualResponse = userController.get(userId);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getFailed() {
        int userId = 1337;
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_FOUND);
        Mockito.when(userService.getUserView(userId)).thenThrow(NoSuchElementException.class);
        ResponseEntity<?> actualResponse = userController.get(userId);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateSuccessful() {
        int userId = 1;
        User updatedUser = new User(null, "newCat", "newDog");
        UserView expectedUser = new UserView(userId, "newCat", "newDog");
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(expectedUser);
        try {
            Mockito.when(userService.update(updatedUser, userId)).thenReturn(expectedUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseEntity<?> actualResponse = userController.update(userId, updatedUser);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateFailedUserNotFound() {
        int userId = 1;
        User updatedUser = new User(null, "newCat", "newDog");
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_FOUND);
        try {
            Mockito.when(userService.update(updatedUser, userId)).thenThrow(Exception.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseEntity<?> actualResponse = userController.update(userId, updatedUser);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateFailedFirstnameNotFound() {
        int userId = 1;
        User updatedUser = new User(null, null, "newDog");
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.FIRSTNAME_NOT_FOUND);
        ResponseEntity<?> actualResponse = userController.update(userId, updatedUser);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateFailedLastnameNotFound() {
        int userId = 1;
        User updatedUser = new User(null, "newCat", null);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.LASTNAME_NOT_FOUND);
        ResponseEntity<?> actualResponse = userController.update(userId, updatedUser);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateFailedFirstnameAndLastnameNotFound() {
        int userId = 1;
        User updatedUser = new User(null, null, null);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.FIRSTNAME_AND_LASTNAME_NOT_FOUND);
        ResponseEntity<?> actualResponse = userController.update(userId, updatedUser);
        assertEquals(expectedResponse, actualResponse);
    }
}