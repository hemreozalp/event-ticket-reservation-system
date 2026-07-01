package com.hemreozalp.event_ticket_reservation_system.event.controller;

import com.hemreozalp.event_ticket_reservation_system.event.dto.CreateEventRequest;
import com.hemreozalp.event_ticket_reservation_system.event.dto.EventFilter;
import com.hemreozalp.event_ticket_reservation_system.event.dto.EventResponse;
import com.hemreozalp.event_ticket_reservation_system.event.dto.UpdateEventRequest;
import com.hemreozalp.event_ticket_reservation_system.event.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> create(@Valid @RequestBody CreateEventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> update(@PathVariable UUID id,
                                                @Valid @RequestBody UpdateEventRequest request) {
        return ResponseEntity.ok(eventService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<EventResponse>> getAll(
            EventFilter filter,
            @PageableDefault(size = 10, sort = "startDate", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(eventService.getAll(filter, pageable));
    }
}
