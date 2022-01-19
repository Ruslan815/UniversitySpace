package ru.ruslan.diploma.chat.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ruslan.diploma.chat.model.User;
import ru.ruslan.diploma.chat.model.UserView;
import ru.ruslan.diploma.chat.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    UserRepository userRepository;

    private final int userId = 1;
    private final String firstName = "cat";
    private final String lastName = "dog";

    @Test
    void createSuccessful() {
        User passedUser = new User(null, firstName, lastName);
        User expectedUser = new User(userId, firstName, lastName);
        UserView expectedUserView = new UserView(expectedUser);
        Mockito.when(userRepository.save(passedUser)).thenReturn(expectedUser);

        UserView actualUserView = userService.create(passedUser);

        assertEquals(expectedUserView, actualUserView);
    }

    @Test
    void getAllUserViewsSuccessful() {
        List<User> list = new ArrayList<>();
        list.add(new User(1, "Fil", "cat"));
        list.add(new User(2, "Emma", "cat"));
        list.add(new User(3, "Dymok", "cat"));

        List<UserView> expectedList = new ArrayList<>();
        expectedList.add(new UserView(1, "Fil", "cat"));
        expectedList.add(new UserView(2, "Emma", "cat"));
        expectedList.add(new UserView(3, "Dymok", "cat"));

        Mockito.when(userRepository.findAll()).thenReturn(list);
        List<UserView> actualList = userService.getAllUserViews();

        assertEquals(expectedList, actualList);
    }

    @Test
    void updateSuccessful() {
        User passedUser = new User(null, firstName, lastName);
        User returnedUser = new User(userId, firstName, lastName);
        UserView expectedUserView = new UserView(userId, firstName, lastName);
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(userRepository.save(passedUser)).thenReturn(returnedUser);

        UserView actualUserView = null;
        try {
            actualUserView = userService.update(passedUser, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expectedUserView, actualUserView);
    }

    @Test
    void updateFailedUserNotFound() {
        User passedUser = new User(null, firstName, lastName);
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(Exception.class, () -> userService.update(passedUser, userId));
    }
}
