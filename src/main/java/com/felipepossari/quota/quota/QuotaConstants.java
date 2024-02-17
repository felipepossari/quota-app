package com.felipepossari.quota.quota;

public class QuotaConstants {
    private QuotaConstants() {
    }

    public static final String API_V1_QUOTA_URL = "/v1/quotas";
    public static final String API_PARAM_PAGE = "page";
    public static final String API_PARAM_PAGE_DEFAULT_VALUE = "0";
    public static final String API_PARAM_PAGE_SIZE = "pageSize";
    public static final String API_PARAM_PAGE_SIZE_DEFAULT_VALUE = "10";

    public static final String API_PATH_VARIABLE_ID = "id";
    public static final String API_HEADER_RATE_LIMIT = "RateLimit-Limit";
    public static final String API_HEADER_RATE_REMAINING = "RateLimit-Remaining";
    public static final String API_HEADER_RATE_RESET = "RateLimit-Reset";

    public static final String QUOTA_TABLE_NAME = "quota";
    public static final String QUOTA_INDEX_NAME = QUOTA_TABLE_NAME;

    public static final String QUOTA_TOPIC_NAME = "quota";
    public static final String QUOTA_TOPIC_GROUP_ID = "quota";
}
