package com.felipepossari.quota.user;

import com.felipepossari.quota.DateTimeUtil;
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
    private final DateTimeUtil dateTimeUtil;

    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setCreatedAt(dateTimeUtil.nowUtc());
        user.setUpdatedAt(dateTimeUtil.nowUtc());
        return repository.getRepository().create(user);
    }

    public User getUser(String userId) {
        return retrieveUser(userId);
    }

    public User updateUser(String userId, User user) {
        var userEntity = retrieveUser(userId);
        userEntity.updateName(user.getFirstName(), user.getLastName(), dateTimeUtil.nowUtc());
        return repository.getRepository().update(userEntity);
    }

    private User retrieveUser(String userId) {
        return repository.getRepository().findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
    }

    public void deleteUser(String userId) {
        var userEntity = retrieveUser(userId);
        repository.getRepository().delete(userEntity);
    }
}
