package io.vicarius.quotaservice.service;

import io.vicarius.quotaservice.dto.User;
import io.vicarius.quotaservice.mapper.UserMapper;
import io.vicarius.quotaservice.repository.UserElasticSearchRepository;
import io.vicarius.quotaservice.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRepositoryService {

    private final UserJpaRepository userJpaRepository;

    private final UserElasticSearchRepository userElasticSearchRepository;

    private final UserMapper userMapper;

    public User createUser(User user) {
        if (isResideInJpa()) {
            var entity = userMapper.toEntity(user);
            return userMapper.toDto(userJpaRepository.save(entity));
        } else {
            var document = userMapper.toDocument(user);
            return userMapper.toDto(userElasticSearchRepository.save(document));
        }
    }

    public User getUser(String userId) {
        if (isResideInJpa()) {
            return userJpaRepository.findById(userId)
                    .map(userMapper::toDto)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        } else {
            return userMapper.toDto(userElasticSearchRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId)));
        }
    }

    public User updateUser(String userId, User updatedUser) {
        if (isResideInJpa()) {
            var user = userJpaRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
            user.setFirstName(updatedUser.firstName());
            user.setLastName(updatedUser.lastName());
            return userMapper.toDto(userJpaRepository.save(user));
        } else {
            var user = userElasticSearchRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
            user.setFirstName(updatedUser.firstName());
            user.setLastName(updatedUser.lastName());
            return userMapper.toDto(userElasticSearchRepository.save(user));
        }
    }

    public void deleteUser(String userId) {
        if (isResideInJpa()) {
            userJpaRepository.deleteById(userId);
        } else {
            userElasticSearchRepository.deleteById(userId);
        }
    }

    /**
     * This method is used to determine if the user should be saved in JPA or ElasticSearch.
     * @return true if the current time is between 9:00 and 17:00, otherwise false.
     */
    private static boolean isResideInJpa() {
        var now = LocalTime.now(ZoneOffset.UTC);
        return now.isAfter(LocalTime.of(9, 0))
                && now.isBefore(LocalTime.of(17, 0));
    }

    public List<User> getAllUsers() {
        var jpaUsers = userJpaRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
        var elasticUsers = new ArrayList<User>();
        for (var document : userElasticSearchRepository.findAll()) {
            elasticUsers.add(userMapper.toDto(document));
        }
        var allUsers = new ArrayList<User>();
        allUsers.addAll(jpaUsers);
        allUsers.addAll(elasticUsers);

        return allUsers;
    }
}
