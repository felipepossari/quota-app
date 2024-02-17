package com.felipepossari.quota.user;

public class UserConstants {
    private UserConstants() {

    }

    public static final String API_LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String API_V1_USER_URL = "/v1/users";
    public static final String API_PATH_VARIABLE_ID_VALUE = "id";
    public static final String API_PATH_VARIABLE_ID = "/{" + API_PATH_VARIABLE_ID_VALUE + "}";
    public static final String API_V1_USER_BY_ID_QUOTA_URL = "/{" + API_PATH_VARIABLE_ID_VALUE + "}/quota";
    public static final String API_V1_USER_BY_ID_QUOTA_URL_PATTERN = "/v1/users/*/quota";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_INDEX_NAME = USER_TABLE_NAME;

    public static final String USER_TOPIC_NAME = "user";
    public static final String USER_TOPIC_GROUP_ID = "user";

}
