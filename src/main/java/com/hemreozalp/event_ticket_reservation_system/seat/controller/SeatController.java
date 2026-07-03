package com.hemreozalp.event_ticket_reservation_system.seat.controller;

import com.hemreozalp.event_ticket_reservation_system.seat.dto.SeatResponse;
import com.hemreozalp.event_ticket_reservation_system.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events/{eventId}/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<SeatResponse>> getSeats(@PathVariable UUID eventId) {
        return ResponseEntity.ok(seatService.getSeatsByEvent(eventId));
    }
}
