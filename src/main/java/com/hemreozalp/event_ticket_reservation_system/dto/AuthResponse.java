package com.hemreozalp.event_ticket_reservation_system.dto;

public record AuthResponse(
        String token,
        String username,
        String role
) {
}
