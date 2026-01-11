/*
Table for storing users
*/
CREATE TABLE IF NOT EXISTS "user"
(
    id           BIGSERIAL PRIMARY KEY,
    email        VARCHAR(255) UNIQUE NOT NULL,
    provider     VARCHAR(50),
    display_name VARCHAR(255),
    picture_url  TEXT,
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    last_login   TIMESTAMP WITH TIME ZONE
);