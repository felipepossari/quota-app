CREATE TABLE user(
    id VARCHAR(36) PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    last_login_time_utc DATETIME,
    created_at DATETIME,
    updated_at DATETIME
)