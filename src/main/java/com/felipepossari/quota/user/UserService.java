package com.felipepossari.quota.user;

import com.felipepossari.quota.common.exception.ResourceNotFoundException;
import com.felipepossari.quota.user.repository.UserRepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.felipepossari.quota.common.exception.ErrorReason.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryFactory repository;

    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        return repository.getRepository("UserEsRepository").create(user);
    }

    public User getUser(String userId) {
        return retrieveUser(userId);
    }

    public User updateUser(String userId, User user) {
        var userEntity = retrieveUser(userId);

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        return repository.getRepository("UserRepositoryFactory").update(userEntity);
    }

    private User retrieveUser(String userId) {
        return repository.getRepository("UserRepositoryFactory").findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
    }

    public void deleteUser(String userId) {
        var userEntity = retrieveUser(userId);
        repository.getRepository("UserRepositoryFactory").delete(userEntity);
    }
}
