package com.hemreozalp.event_ticket_reservation_system.reservation.dto;

import com.hemreozalp.event_ticket_reservation_system.reservation.entity.ReservationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationResponse(
        UUID id,
        UUID eventId,
        Long seatId,
        ReservationStatus status,
        LocalDateTime reservationTime
) {
}
