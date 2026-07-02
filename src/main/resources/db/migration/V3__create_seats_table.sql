CREATE TABLE seat (
    id BIGSERIAL PRIMARY KEY,

    seat_number VARCHAR(20) NOT NULL,
    row_number VARCHAR(20) NOT NULL,

    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',

    event_id BIGINT NOT NULL,

    CONSTRAINT fk_seat_event
        FOREIGN KEY (event_id)
        REFERENCES event(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_seat_event ON seat(event_id);