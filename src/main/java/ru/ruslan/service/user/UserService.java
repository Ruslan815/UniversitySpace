package ru.ruslan.service.user;

import org.springframework.transaction.annotation.Transactional;
import ru.ruslan.entity.chat.Chat;
import ru.ruslan.entity.chat.ChatView;
import ru.ruslan.entity.user.Role;
import ru.ruslan.entity.user.User;
import ru.ruslan.entity.user.UserView;
import ru.ruslan.repository.user.RoleRepository;
import ru.ruslan.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private final Set<Long> listeningUsers = Collections.synchronizedSet(new HashSet<>());
    private final Set<Long> updateUserConnection = Collections.synchronizedSet(new HashSet<>());

    public boolean isUserExist(Long userId) {
        if (userId == null) return false;
        return userRepository.existsById(userId);
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;
        return user.getId();
    }

    public UserView getUserViewByUsername(String username) {
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

    public boolean createUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role((long) ROLES.ROLE_USER.ordinal(), "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public String giveAdminRoleById(Long userId) throws Exception {
        if (!isUserExist(userId)) {
            throw new Exception("Not found user with id: " + userId);
        }
        User user = userRepository.findById(userId).orElseThrow();
        user.getRoles().add(new Role((long) ROLES.ROLE_ADMIN.ordinal(), "ROLE_ADMIN"));
        userRepository.save(user);

        return "Admin role was granted successfully!";
    }

    public String deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return "User was deleted successfully!";
        } else {
            return "Not found user with id: " + userId;
        }
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

    @Transactional
    public void incrementSolvedTaskCount(User user) {
        Long solvedTaskCount = user.getSolvedTaskCount();
        solvedTaskCount++;
        user.setSolvedTaskCount(solvedTaskCount);
    }

    public List<ChatView> getAvailableChatsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        List<ChatView> answerList = new ArrayList<>();
        for (Chat chat : user.getAvailableChats()) {
            answerList.add(new ChatView(chat));
        }
        return answerList;
    }

    public boolean isUserListening(Long userId) {
        return listeningUsers.contains(userId);
    }

    public void addListeningUser(Long userId) {
        listeningUsers.add(userId);
    }

    public void removeListeningUser(Long userId) {
        listeningUsers.remove(userId);
    }

    public boolean isUserConnectionShouldBeUpdated(Long userId) {
        return updateUserConnection.contains(userId);
    }

    public void addUserConnectionToUpdate(Long userId) {
        updateUserConnection.add(userId);
    }

    public void removeUserConnectionToUpdate(Long userId) {
        updateUserConnection.remove(userId);
    }

    private enum ROLES {
        ROLE_ADMIN,
        ROLE_USER
    }
}
