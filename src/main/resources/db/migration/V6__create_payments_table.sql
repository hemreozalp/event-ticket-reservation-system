CREATE TABLE payments (
    id UUID PRIMARY KEY,

    reservation_id UUID NOT NULL UNIQUE,

    amount DECIMAL(10,2) NOT NULL CHECK (amount >= 0),

    status VARCHAR(20) NOT NULL,

    payment_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_payment_reservation
        FOREIGN KEY (reservation_id)
        REFERENCES reservations(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_payment_status ON payments(status);