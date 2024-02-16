package com.felipepossari.quota.user;

import com.felipepossari.quota.user.api.model.UserRequest;
import com.felipepossari.quota.user.api.model.UserResponse;
import com.felipepossari.quota.user.repository.elasticsearch.model.UserIndex;
import com.felipepossari.quota.user.repository.mysql.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {

    public User toUser(UserRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .lastLogin(user.getLastLoginTimeUtc())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .lastLoginTimeUtc(user.getLastLoginTimeUtc())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public User toUser(UserEntity user) {
        return User.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .lastLoginTimeUtc(user.getLastLoginTimeUtc())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public User toUser(UserIndex user) {
        return User.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .lastLoginTimeUtc(user.getLastLoginTimeUtc())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public UserIndex toIndex(User user) {
        return UserIndex.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .lastLoginTimeUtc(user.getLastLoginTimeUtc())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
