/*
Table for storing notes
*/

CREATE TABLE IF NOT EXISTS note (
    id BIGSERIAL PRIMARY KEY,
    title varchar(256),
    content text,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
            REFERENCES "user"(id)
            ON DELETE CASCADE
)