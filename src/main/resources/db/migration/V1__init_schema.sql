-- =========================
-- USERS TABLE
-- =========================

CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- =========================
-- INDEXES (performance)
-- =========================

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);