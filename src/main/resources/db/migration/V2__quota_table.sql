CREATE TABLE quota(
    rate_key VARCHAR(36) PRIMARY KEY,
    quota_limit INT NOT NULL,
    remaining INT NOT NULL,
    reset_time DATETIME
)