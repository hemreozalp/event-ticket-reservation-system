package com.hemreozalp.event_ticket_reservation_system.dto;

import com.hemreozalp.event_ticket_reservation_system.entity.enums.EventStatus;

import java.math.BigDecimal;

public record EventFilter(
        String title,
        String location,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        EventStatus status
) {
}
