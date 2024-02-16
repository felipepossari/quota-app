package com.felipepossari.quota.user;

import com.felipepossari.quota.DateTimeUtil;
import com.felipepossari.quota.common.exception.ResourceNotFoundException;
import com.felipepossari.quota.user.repository.UserRepositoryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.felipepossari.quota.common.exception.ErrorReason.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepositoryFactory repository;
    private final DateTimeUtil dateTimeUtil;
    private final UserProducer producer;

    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        log.info("Creating user. UserId: {}", user.getId());
        user.setCreatedAt(dateTimeUtil.nowUtc());
        user.setUpdatedAt(dateTimeUtil.nowUtc());
        var newUser = repository.getRepository().create(user);
        producer.sendUserCreatedMessage(newUser);
        return newUser;
    }

    public User getUser(String userId) {
        return retrieveUser(userId);
    }

    public User updateUser(String userId, User user) {
        log.info("Updating user. UserId: {}", user.getId());
        var userEntity = retrieveUser(userId);
        userEntity.updateName(user.getFirstName(), user.getLastName(), dateTimeUtil.nowUtc());
        var userUpdated = repository.getRepository().update(userEntity);
        producer.sendUserUpdatedMessage(userUpdated);
        return userUpdated;
    }

    private User retrieveUser(String userId) {
        return repository.getRepository().findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
    }

    public void deleteUser(String userId) {
        log.info("Deleting user. UserId: {}", userId);
        var user = retrieveUser(userId);
        repository.getRepository().delete(user);
        producer.sendUserDeletedMessage(user);
    }

    public void syncUserCreated(User user) {
        log.info("Syncing user created. UserId: {}", user.getId());
        var userOpt = repository.getIdleRepository().findById(user.getId());
        if (userOpt.isPresent()) {
            log.info("User already registered. Skipping sync for user created. UserId: {}", user.getId());
        } else {
            repository.getIdleRepository().create(user);
        }
    }

    public void syncUserDeleted(User user) {
        log.info("Syncing user deleted. UserId: {}", user.getId());
        repository.getIdleRepository().delete(user);
    }

    public void syncUserUpdated(User user) {
        log.info("Syncing user updated. UserId: {}", user.getId());
        var userOpt = repository.getIdleRepository().findById(user.getId());
        if (userOpt.isPresent()) {
            if (user.getUpdatedAt().isBefore(userOpt.get().getUpdatedAt())) {
                log.info("UpdatedAt time from registered user is after the event. Skipping sync for user updated. UserId: {}", user.getId());
            }
        } else {
            log.info("User not registered. Creating user from user updated event. UserId: {}", user.getId());
            repository.getIdleRepository().create(user);
        }
    }
}
