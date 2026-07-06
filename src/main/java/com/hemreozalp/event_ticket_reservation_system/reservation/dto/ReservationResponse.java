package com.hemreozalp.event_ticket_reservation_system.reservation.dto;

import com.hemreozalp.event_ticket_reservation_system.reservation.entity.ReservationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationResponse(
        UUID uuid,
        UUID eventId,
        ReservationStatus status,
        LocalDateTime reservationTime
) {
}
