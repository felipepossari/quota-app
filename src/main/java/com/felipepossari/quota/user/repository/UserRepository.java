package com.felipepossari.quota.user.repository;

import com.felipepossari.quota.user.User;

import java.util.Optional;

public interface UserRepository {

    User create(User user);

    Optional<User> findById(String userId);

    User update(User user);

    void delete(User user);
}
