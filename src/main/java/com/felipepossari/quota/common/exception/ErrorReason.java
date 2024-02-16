package com.felipepossari.quota.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorReason {

    USER_NOT_FOUND("U001", "User not found"),
    TOO_MANY_REQUESTS("U002", "Too many requests");

    private String code;
    private String message;
}
