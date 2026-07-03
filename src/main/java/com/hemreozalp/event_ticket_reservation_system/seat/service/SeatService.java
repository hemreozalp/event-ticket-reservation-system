package com.hemreozalp.event_ticket_reservation_system.seat.service;

import com.hemreozalp.event_ticket_reservation_system.event.repository.EventRepository;
import com.hemreozalp.event_ticket_reservation_system.seat.dto.SeatResponse;
import com.hemreozalp.event_ticket_reservation_system.seat.mapper.SeatMapper;
import com.hemreozalp.event_ticket_reservation_system.seat.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final EventRepository eventRepository;
    private final SeatMapper seatMapper;

    public List<SeatResponse> getSeatsByEvent(UUID eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found (Resource not found)"));

        return seatMapper.toResponseList(
                seatRepository.findByEventId(eventId)
        );
    }
}
