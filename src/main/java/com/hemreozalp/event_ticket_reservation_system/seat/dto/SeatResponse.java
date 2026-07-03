package com.hemreozalp.event_ticket_reservation_system.seat.dto;

import com.hemreozalp.event_ticket_reservation_system.seat.entity.SeatStatus;

public record SeatResponse(
        Long id,
        String seatNumber,
        String rowNumber,
        SeatStatus status
) {
}
