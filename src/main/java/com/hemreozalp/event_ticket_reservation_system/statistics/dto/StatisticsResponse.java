package com.hemreozalp.event_ticket_reservation_system.statistics.dto;

public record StatisticsResponse(
        long totalEvents,
        long totalUsers,
        long totalReservations,
        long occupiedSeats
) {
}
