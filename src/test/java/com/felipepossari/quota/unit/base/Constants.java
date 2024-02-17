package com.felipepossari.quota.unit.base;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Constants {

    public static final String USER_ID = "9b0dc467-f3b2-4cf9-9b90-f19d4ae74200";
    public static final String FIRST_NAME = "Name";
    public static final String LAST_NAME = "Other";
    public static final String FIRST_NAME_UPDATED = "Updated";
    public static final String LAST_NAME_UPDATED = "Name";
    public static final LocalDateTime CREATED_AT = LocalDateTime.now(ZoneOffset.UTC);
    public static final LocalDateTime UPDATED_AT = LocalDateTime.now(ZoneOffset.UTC);


    public static final String RATE_KEY = "9b0dc467-f3b2-4cf9-9b90-f19d4ae74200";
    public static final Integer QUOTA_LIMIT = 5;
    public static final Integer REMAINING_LIMIT = 5;
    public static final LocalDateTime RESET_TIME = LocalDateTime.now(ZoneOffset.UTC).plusYears(10);

    public static final OffsetDateTime EVENT_TIME = OffsetDateTime.now(ZoneOffset.UTC);
    public static final String EVENT_ID = "7971a04b-edd6-4986-8e22-0c2d9aca70f7";
}
