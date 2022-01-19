package ru.ruslan.diploma.service;

import org.springframework.stereotype.Service;
import ru.ruslan.diploma.model.User;
import ru.ruslan.diploma.model.UserView;
import ru.ruslan.diploma.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserView create(User someUser) {
        return new UserView(userRepository.save(someUser));
    }

    public List<UserView> getAllUserViews() {
        List<User> tempList = userRepository.findAll();
        List<UserView> responseList = new ArrayList<>();
        for (User tempUser : tempList) {
            responseList.add(new UserView(tempUser));
        }
        return responseList;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(int userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public UserView getUserView(int userId) {
        return new UserView(userRepository.findById(userId).orElseThrow());
    }

    public UserView update(User someUser, int userId) throws Exception {
        if (userRepository.existsById(userId)) {
            someUser.setId(userId);
            return new UserView(userRepository.save(someUser));
        }
        throw new Exception();
    }

    public boolean isUserExist(Integer userId) {
        if (userId == null) return false;
        return userRepository.existsById(userId);
    }
}