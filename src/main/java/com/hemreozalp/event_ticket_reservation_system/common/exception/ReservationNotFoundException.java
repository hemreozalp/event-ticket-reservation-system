package com.hemreozalp.event_ticket_reservation_system.common.exception;

import java.util.UUID;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(UUID reservationId) {
        super("Reservation not found with id: " + reservationId);
    }
}