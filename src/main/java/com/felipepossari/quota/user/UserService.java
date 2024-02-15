package com.felipepossari.quota.user;

import com.felipepossari.quota.common.exception.ResourceNotFoundException;
import com.felipepossari.quota.user.repository.model.UserEntity;
import com.felipepossari.quota.user.repository.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.felipepossari.quota.common.exception.ErrorReason.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserBuilder builder;
    private final UserRepository repository;

    public User createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        repository.save(builder.toEntity(user));
        return user;
    }

    public User getUser(String userId) {
        var userEntity = retrieveUser(userId);
        return builder.toUser(userEntity);
    }

    private UserEntity retrieveUser(String userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
    }
}
