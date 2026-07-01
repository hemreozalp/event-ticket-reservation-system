package com.hemreozalp.event_ticket_reservation_system.dto;

import com.hemreozalp.event_ticket_reservation_system.entity.enums.EventStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateEventRequest(
        @NotBlank
        String title,

        String description,

        @NotBlank
        String location,

        @NotNull
        LocalDateTime startDate,

        @NotNull
        LocalDateTime endDate,

        @NotNull
        Integer capacity,

        @NotNull
        BigDecimal price,

        @NotNull
        EventStatus status
) {
}
