package com.hemreozalp.event_ticket_reservation_system.reservation.controller;

import com.hemreozalp.event_ticket_reservation_system.reservation.dto.ReservationResponse;
import com.hemreozalp.event_ticket_reservation_system.reservation.dto.ReserveSeatRequest;
import com.hemreozalp.event_ticket_reservation_system.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(@RequestBody @Valid ReserveSeatRequest request) {
        ReservationResponse response = reservationService.reserve(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ReservationResponse>> myReservations(@RequestParam UUID userId) {
        return ResponseEntity.ok(reservationService.myReservation(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        reservationService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
