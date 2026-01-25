CREATE TABLE one_time_tokens (
    token_value VARCHAR(64) NOT NULL PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    expires_at TIMESTAMP NOT NULL
);
