CREATE TABLE reservations (
    id UUID PRIMARY KEY,

    user_id BIGINT NOT NULL,
    event_id UUID NOT NULL,
    seat_id BIGINT NOT NULL,

    reservation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    status VARCHAR(20) NOT NULL,

    CONSTRAINT fk_reservation_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_reservation_event
        FOREIGN KEY (event_id)
        REFERENCES events(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_reservation_seat
        FOREIGN KEY (seat_id)
        REFERENCES seat(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_reservation_user ON reservations(user_id);
CREATE INDEX idx_reservation_event ON reservations(event_id);
CREATE INDEX idx_reservation_seat ON reservations(seat_id);
CREATE INDEX idx_reservation_status ON reservations(status);