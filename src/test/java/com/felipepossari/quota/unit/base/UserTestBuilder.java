package com.felipepossari.quota.unit.base;

import com.felipepossari.quota.user.User;

import java.time.LocalDateTime;

import static com.felipepossari.quota.unit.base.Constants.CREATED_AT;
import static com.felipepossari.quota.unit.base.Constants.FIRST_NAME;
import static com.felipepossari.quota.unit.base.Constants.FIRST_NAME_UPDATED;
import static com.felipepossari.quota.unit.base.Constants.LAST_NAME;
import static com.felipepossari.quota.unit.base.Constants.LAST_NAME_UPDATED;
import static com.felipepossari.quota.unit.base.Constants.UPDATED_AT;
import static com.felipepossari.quota.unit.base.Constants.USER_ID;

public class UserTestBuilder {

    private String id = USER_ID;
    private String firstName = FIRST_NAME;
    private String lastName = LAST_NAME;
    private LocalDateTime lastLoginTimeUtc;
    private LocalDateTime createdAt = CREATED_AT;
    private LocalDateTime updatedAt = UPDATED_AT;

    private UserTestBuilder() {
    }

    public static UserTestBuilder anUser() {
        return new UserTestBuilder();
    }

    public static UserTestBuilder anUserUpdated() {
        var userTestBuilder = new UserTestBuilder();
        userTestBuilder.firstName(FIRST_NAME_UPDATED);
        userTestBuilder.lastName(LAST_NAME_UPDATED);
        userTestBuilder.updatedAt(userTestBuilder.updatedAt.plusSeconds(30));
        return userTestBuilder;
    }

    public UserTestBuilder id(String id) {
        this.id = id;
        return this;
    }

    public UserTestBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserTestBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserTestBuilder lastLoginTimeUtc(LocalDateTime lastLoginTimeUtc) {
        this.lastLoginTimeUtc = lastLoginTimeUtc;
        return this;
    }

    public UserTestBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserTestBuilder updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public User build() {
        return User.builder()
                .lastLoginTimeUtc(lastLoginTimeUtc)
                .lastName(lastName)
                .firstName(firstName)
                .id(id)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
