package com.felipepossari.quota.user;

import com.felipepossari.quota.user.repository.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
}
