package com.felipepossari.quota.user;

import com.felipepossari.quota.common.exception.ResourceNotFoundException;
import com.felipepossari.quota.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.felipepossari.quota.common.exception.ErrorReason.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        return repository.create(user);
    }

    public User getUser(String userId) {
        return retrieveUser(userId);
    }

    public User updateUser(String userId, User user) {
        var userEntity = retrieveUser(userId);

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        return repository.update(userEntity);
    }

    private User retrieveUser(String userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
    }

    public void deleteUser(String userId) {
        var userEntity = retrieveUser(userId);
        repository.delete(userEntity);
    }
}
