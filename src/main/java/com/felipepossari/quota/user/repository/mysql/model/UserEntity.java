package com.felipepossari.quota.user.repository.mysql.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.felipepossari.quota.user.UserConstants.USER_TABLE_NAME;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = USER_TABLE_NAME)
public class UserEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private LocalDateTime lastLoginTimeUtc;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
