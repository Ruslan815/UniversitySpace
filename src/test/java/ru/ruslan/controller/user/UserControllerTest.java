package ru.ruslan.controller.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ruslan.service.user.UserService;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    private final Long userId = 1L;
    private final String userName = "username";

    @Test
    public void getUserIdByUsernameSuccessful() {
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body(userId);
        Mockito.when(userService.getUserIdByUsername(userName)).thenReturn(userId);

        ResponseEntity<?> actualResponse = userController.getUserIdByUsername(userName);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getUserIdByUsernameFailedInvalidUsername() {
        ResponseEntity<?> expectedResponse = ResponseEntity.status(500).body("Not found user with username: " + userName);
        Mockito.when(userService.getUserIdByUsername(userName)).thenReturn(null);

        ResponseEntity<?> actualResponse = userController.getUserIdByUsername(userName);

        assertEquals(expectedResponse, actualResponse);
    }
}