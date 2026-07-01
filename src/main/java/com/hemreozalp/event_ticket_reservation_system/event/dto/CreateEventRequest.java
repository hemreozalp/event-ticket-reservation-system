package com.hemreozalp.event_ticket_reservation_system.event.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateEventRequest(
        @NotBlank
        String title,

        String description,

        @NotBlank
        String location,

        @NotNull
        @Future
        LocalDateTime startDate,

        @NotNull
        @Future
        LocalDateTime endDate,

        @NotNull
        @Positive
        Integer capacity,

        @NotNull
        @PositiveOrZero
        BigDecimal price
) {
}
