CREATE TABLE events (
    id UUID PRIMARY KEY,

    title VARCHAR(100) NOT NULL,
    description TEXT,
    location VARCHAR(255) NOT NULL,

    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,

    capacity INTEGER NOT NULL CHECK (capacity > 0),
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),

    status VARCHAR(20) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);