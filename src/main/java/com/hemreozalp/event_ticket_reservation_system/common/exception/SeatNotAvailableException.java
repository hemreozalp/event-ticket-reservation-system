package com.hemreozalp.event_ticket_reservation_system.common.exception;

public class SeatNotAvailableException extends RuntimeException {

    public SeatNotAvailableException(Long seatId) {
        super("Seat is not available. Seat id: " + seatId);
    }
}