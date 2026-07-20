package com.hemreozalp.event_ticket_reservation_system.common.exception;

public class SeatAlreadyReservedException extends RuntimeException {

    public SeatAlreadyReservedException(Long seatId) {
        super("Seat is already reserved. Seat id: " + seatId);
    }
}