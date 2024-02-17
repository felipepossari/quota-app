package com.felipepossari.quota.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorReason {

    FIRST_NAME_EMPTY("R001", "First name cannot be null or empty"),
    LAST_NAME_EMPTY("R002", "Second name cannot be null or empty"),

    USER_NOT_FOUND("A001", "User not found"),
    TOO_MANY_REQUESTS("A002", "Too many requests"),
    EVENT_PARSE_FAIL("A003", "Failure to parse event to String"),
    EVENT_MESSAGE_PARSE_FAIL("A004", "Failure to parse event message"),
    USER_PARSE_FAIL("A005", "Failure to parse user to String"),
    USER_DATA_PARSE_FAIL("A006", "Failure to parse user data"),
    QUOTA_PARSE_FAIL("A007", "Failure to parse quota to String"),
    QUOTA_DATA_PARSE_FAIL("A008", "Failure to parse quota data"),

    INTERNAL_ERROR("U999", "Internal error");

    private final String code;
    private final String message;
}
