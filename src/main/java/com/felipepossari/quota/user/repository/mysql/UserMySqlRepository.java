package com.felipepossari.quota.user.repository.mysql;

import com.felipepossari.quota.user.User;
import com.felipepossari.quota.user.UserBuilder;
import com.felipepossari.quota.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserMySqlRepository implements UserRepository {

    private final UserJpaRepository repository;
    private final UserBuilder builder;

    @Override
    public User create(User user) {
        user.setId(UUID.randomUUID().toString());
        var userCreated = repository.save(builder.toEntity(user));
        return builder.toUser(userCreated);
    }

    @Override
    public Optional<User> findById(String userId) {
        var userOpt = repository.findById(userId);
        return userOpt.map(builder::toUser);
    }

    @Override
    public User update(User user) {
        var userUpdated = repository.save(builder.toEntity(user));
        return builder.toUser(userUpdated);
    }

    @Override
    public void delete(User user) {
        repository.deleteById(user.getId());
    }
}