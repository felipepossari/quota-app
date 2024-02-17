package com.felipepossari.quota.user.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.felipepossari.quota.user.UserConstants.API_LOCAL_DATE_TIME_FORMAT;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = API_LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime lastLogin;
    @JsonFormat(pattern = API_LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime createdAt;
    @JsonFormat(pattern = API_LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;
}
