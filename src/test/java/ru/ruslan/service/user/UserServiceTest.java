package ru.ruslan.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ruslan.entity.user.User;
import ru.ruslan.repository.user.UserRepository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final String userName = "username";
    private final String password = "password";

    @Test
    public void createUserSuccessful() {
        User user = new User();
        user.setUsername(userName);
        Mockito.when(userRepository.findByUsername(userName)).thenReturn(null);
        Mockito.when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn(password);
        Mockito.when(userRepository.save(user)).thenReturn(null);

        boolean actual = userService.createUser(user);

        assertTrue(actual);
    }

    @Test
    public void createUserFailedUserAlreadyExists() {
        User user = new User();
        user.setUsername(userName);

        Mockito.when(userRepository.findByUsername(userName)).thenReturn(user);
        Mockito.when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn(password);
        Mockito.when(userRepository.save(user)).thenReturn(null);

        boolean actual = userService.createUser(user);

        assertFalse(actual);
    }
}