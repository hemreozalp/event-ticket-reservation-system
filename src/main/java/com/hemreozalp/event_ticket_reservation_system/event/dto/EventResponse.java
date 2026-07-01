package com.hemreozalp.event_ticket_reservation_system.event.dto;

import com.hemreozalp.event_ticket_reservation_system.event.entity.EventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record EventResponse(
        UUID id,
        String title,
        String description,
        String location,

        LocalDateTime startDate,
        LocalDateTime endDate,

        Integer capacity,
        BigDecimal price,

        EventStatus status,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
