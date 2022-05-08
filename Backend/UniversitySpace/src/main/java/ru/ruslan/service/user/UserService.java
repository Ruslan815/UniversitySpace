package ru.ruslan.service.user;

import org.springframework.transaction.annotation.Transactional;
import ru.ruslan.entity.user.Role;
import ru.ruslan.entity.user.User;
import ru.ruslan.repository.user.RoleRepository;
import ru.ruslan.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean isUserExist(Long userId) {
        if (userId == null) return false;
        return userRepository.existsById(userId);
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public UserView findUserByUsername(String username) {
        return new UserView(userRepository.findByUsername(username));
    }

    public List<UserView> allUsers() {
         List<User> list = userRepository.findAll();
         List<UserView> answerList = new ArrayList<>();
         for (User user : list) {
             answerList.add(new UserView(user));
         }

         return answerList;
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role((long) ROLES.ROLE_USER.ordinal(), "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        System.out.println("User was saved to DB: " + user);

        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Transactional
    public void depositToUserBalance(User someUser, Double amount) {
        User user = findUserById(someUser.getId());
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
    }

    @Transactional
    public void withdrawFromUserBalance(User someUser, Double amount) {
        depositToUserBalance(someUser, -amount);
    }

    private enum ROLES {
        ROLE_ADMIN,
        ROLE_USER
    }
}
