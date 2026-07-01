package com.hemreozalp.event_ticket_reservation_system.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String password
) {
}
