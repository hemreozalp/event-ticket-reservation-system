package com.hemreozalp.event_ticket_reservation_system.reservation.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReserveSeatRequest(
        @NotNull(message = "User id is required")
        UUID userId,

        @NotNull(message = "Seat id is required")
        Long seatId
) {
}
