package com.hemreozalp.event_ticket_reservation_system.service;

import com.hemreozalp.event_ticket_reservation_system.dto.CreateEventRequest;
import com.hemreozalp.event_ticket_reservation_system.dto.EventResponse;
import com.hemreozalp.event_ticket_reservation_system.dto.UpdateEventRequest;
import com.hemreozalp.event_ticket_reservation_system.entity.Event;
import com.hemreozalp.event_ticket_reservation_system.entity.enums.EventStatus;
import com.hemreozalp.event_ticket_reservation_system.mapper.EventMapper;
import com.hemreozalp.event_ticket_reservation_system.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

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
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (request.endDate().isBefore(request.startDate())) {
            throw new IllegalArgumentException("Invalid date range");
        }

        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setLocation(request.location());
        event.setStartDate(request.startDate());
        event.setEndDate(request.endDate());
        event.setCapacity(request.capacity());
        event.setPrice(request.price());
        event.setStatus(request.status());

        return eventMapper.toResponse(event);
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
    public List<EventResponse> getAll() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::toResponse)
                .toList();
    }
}
