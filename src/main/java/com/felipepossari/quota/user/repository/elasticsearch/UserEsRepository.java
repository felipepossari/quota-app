package com.felipepossari.quota.user.repository.elasticsearch;

import com.felipepossari.quota.user.User;
import com.felipepossari.quota.user.UserBuilder;
import com.felipepossari.quota.user.repository.UserRepository;
import com.felipepossari.quota.user.repository.elasticsearch.UserElasticsearchRepository;
import com.felipepossari.quota.user.repository.elasticsearch.model.UserIndex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.data.elasticsearch.core.RefreshPolicy.IMMEDIATE;

@Component("UserEsRepository")
@RequiredArgsConstructor
public class UserEsRepository implements UserRepository {

    private final UserBuilder builder;
    private final UserElasticsearchRepository repository;

    @Override
    public User create(User user) {
        UserIndex index = builder.toIndex(user);
        var userCreated = repository.save(index, IMMEDIATE);
        return builder.toUser(userCreated);
    }

    @Override
    public Optional<User> findById(String userId) {
        var userOpt = repository.findById(userId);
        return userOpt.map(builder::toUser);
    }

    @Override
    public User update(User user) {
        var userIndex = builder.toIndex(user);
        var userUpdated = repository.save(userIndex, IMMEDIATE);
        return builder.toUser(userUpdated);
    }

    @Override
    public void delete(User user) {
        repository.deleteById(user.getId(), IMMEDIATE);
    }
}
