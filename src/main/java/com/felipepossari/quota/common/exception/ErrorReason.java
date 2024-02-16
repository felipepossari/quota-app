package com.felipepossari.quota.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorReason {

    FIRST_NAME_EMPTY("R001", "First name cannot be null or empty"),
    LAST_NAME_EMPTY("R002", "Second name cannot be null or empty"),

    USER_NOT_FOUND("U001", "User not found"),
    TOO_MANY_REQUESTS("U002", "Too many requests"),
    EVENT_PARSE_FAIL("U003", "Failure to parse event to String"),
    USER_PARSE_FAIL("U004", "Failure to parse user to String"),

    INTERNAL_ERROR("U999", "Internal error");

    private String code;
    private String message;
}
