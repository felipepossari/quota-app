package com.felipepossari.quota.user.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
