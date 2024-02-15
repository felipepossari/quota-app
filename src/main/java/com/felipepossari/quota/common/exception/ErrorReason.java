package com.felipepossari.quota.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorReason {

    USER_NOT_FOUND("U001", "User not found");

    private String code;
    private String message;
}
