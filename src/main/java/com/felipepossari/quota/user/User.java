package com.felipepossari.quota.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDateTime lastLoginTimeUtc;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updateName(String firstName, String lastName, LocalDateTime updateTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.updatedAt = updateTime;
    }
}
