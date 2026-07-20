package com.hemreozalp.event_ticket_reservation_system.event.service;

import com.hemreozalp.event_ticket_reservation_system.common.exception.EventNotFoundException;
import com.hemreozalp.event_ticket_reservation_system.event.dto.CreateEventRequest;
import com.hemreozalp.event_ticket_reservation_system.event.dto.EventFilter;
import com.hemreozalp.event_ticket_reservation_system.event.dto.EventResponse;
import com.hemreozalp.event_ticket_reservation_system.event.dto.UpdateEventRequest;
import com.hemreozalp.event_ticket_reservation_system.event.entity.Event;
import com.hemreozalp.event_ticket_reservation_system.event.entity.EventStatus;
import com.hemreozalp.event_ticket_reservation_system.event.mapper.EventMapper;
import com.hemreozalp.event_ticket_reservation_system.event.repository.EventRepository;
import com.hemreozalp.event_ticket_reservation_system.event.repository.EventSpecification;
import com.hemreozalp.event_ticket_reservation_system.seat.entity.SeatStatus;
import com.hemreozalp.event_ticket_reservation_system.seat.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final SeatRepository seatRepository;

    public EventResponse create(CreateEventRequest request) {
        if (request.endDate().isBefore(request.startDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        Event event = eventMapper.toEntity(request);
        event.setStatus(EventStatus.ACTIVE);

        Event saved = eventRepository.save(event);

        return eventMapper.toResponse(saved);
    }

    public EventResponse update(UUID id, UpdateEventRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));

        if (request.endDate().isBefore(request.startDate())) {
            throw new IllegalArgumentException("Invalid date range");
        }

        eventMapper.updateEntity(request, event);

        Event saved = eventRepository.save(event);

        return eventMapper.toResponse(saved);
    }

    public void delete(UUID id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found");
        }

        eventRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public EventResponse getById(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return eventMapper.toResponse(event);
    }

    @Transactional(readOnly = true)
    public Page<EventResponse> getAll(EventFilter filter, Pageable pageable) {
        return eventRepository.findAll(
                        EventSpecification.filter(filter),
                        pageable)
                .map(eventMapper::toResponse);
    }

    private EventResponse toResponse(Event event) {

        long availableSeats = seatRepository.countByEventIdAndStatus(
                event.getId(),
                SeatStatus.AVAILABLE
        );

        EventResponse response = eventMapper.toResponse(event);

        return new EventResponse(
                response.id(),
                response.title(),
                response.description(),
                response.location(),
                response.startDate(),
                response.endDate(),
                response.capacity(),
                availableSeats,
                response.price(),
                response.status(),
                response.createdAt(),
                response.updatedAt()
        );
    }
}
