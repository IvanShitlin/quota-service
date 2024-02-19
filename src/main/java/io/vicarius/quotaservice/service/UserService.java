package io.vicarius.quotaservice.service;

import io.vicarius.quotaservice.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryService userRepositoryService;

    public User createUser(User user) {
        return userRepositoryService.createUser(user);
    }

    public User getUser(String userId) {
        return userRepositoryService.getUser(userId);
    }

    public User updateUser(String userId, User updatedUser) {
        var user = userRepositoryService.getUser(userId);
        var newUser = new User(userId, updatedUser.firstName(), updatedUser.lastName(), user.lastLoginTimeUtc());
        userRepositoryService.createUser(newUser);
        return newUser;
    }

    public void deleteUser(String userId) {
        userRepositoryService.deleteUser(userId);
    }

    public List<User> getAllUsers() {
        return userRepositoryService.getAllUsers();
    }

}
