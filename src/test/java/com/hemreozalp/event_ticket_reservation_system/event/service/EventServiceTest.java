package com.hemreozalp.event_ticket_reservation_system.event.service;

import com.hemreozalp.event_ticket_reservation_system.common.exception.EventNotFoundException;
import com.hemreozalp.event_ticket_reservation_system.event.dto.CreateEventRequest;
import com.hemreozalp.event_ticket_reservation_system.event.dto.EventResponse;
import com.hemreozalp.event_ticket_reservation_system.event.dto.UpdateEventRequest;
import com.hemreozalp.event_ticket_reservation_system.event.entity.Event;
import com.hemreozalp.event_ticket_reservation_system.event.entity.EventStatus;
import com.hemreozalp.event_ticket_reservation_system.event.mapper.EventMapper;
import com.hemreozalp.event_ticket_reservation_system.event.repository.EventRepository;
import com.hemreozalp.event_ticket_reservation_system.seat.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private EventService eventService;


    private Event event;
    private EventResponse response;
    private CreateEventRequest createRequest;
    private UpdateEventRequest updateRequest;


    @BeforeEach
    void setUp() {

        event = Event.builder()
                .id(UUID.randomUUID())
                .title("Concert")
                .description("Rock concert")
                .location("Istanbul")
                .startDate(LocalDateTime.now().plusDays(10))
                .endDate(LocalDateTime.now().plusDays(11))
                .capacity(100)
                .price(BigDecimal.valueOf(500))
                .status(EventStatus.ACTIVE)
                .build();


        createRequest = new CreateEventRequest(
                "Concert",
                "Rock concert",
                "Istanbul",
                LocalDateTime.now().plusDays(10),
                LocalDateTime.now().plusDays(11),
                100,
                BigDecimal.valueOf(500)
        );


        updateRequest = new UpdateEventRequest(
                "Updated Concert",
                "Updated description",
                "Ankara",
                LocalDateTime.now().plusDays(15),
                LocalDateTime.now().plusDays(16),
                200,
                BigDecimal.valueOf(700),
                EventStatus.ACTIVE
        );


        response = new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getLocation(),
                event.getStartDate(),
                event.getEndDate(),
                event.getCapacity(),
                100L,
                event.getPrice(),
                event.getStatus(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void create_shouldCreateEventSuccessfully() {

        when(eventMapper.toEntity(createRequest))
                .thenReturn(event);

        when(eventRepository.save(event))
                .thenReturn(event);

        when(eventMapper.toResponse(event))
                .thenReturn(response);


        EventResponse result = eventService.create(createRequest);


        assertEquals(response, result);

        assertEquals(
                EventStatus.ACTIVE,
                event.getStatus()
        );


        verify(eventMapper)
                .toEntity(createRequest);

        verify(eventRepository)
                .save(event);

        verify(eventMapper)
                .toResponse(event);
    }

    @Test
    void create_shouldThrowIllegalArgumentExceptionWhenDateInvalid() {

        CreateEventRequest invalidRequest = new CreateEventRequest(
                "Concert",
                "Rock concert",
                "Istanbul",
                LocalDateTime.now().plusDays(10),
                LocalDateTime.now().plusDays(5),
                100,
                BigDecimal.valueOf(500)
        );


        assertThrows(
                IllegalArgumentException.class,
                () -> eventService.create(invalidRequest)
        );


        verify(eventMapper, never())
                .toEntity(any());

        verify(eventRepository, never())
                .save(any());
    }

    @Test
    void getById_shouldReturnEvent() {

        UUID eventId = event.getId();

        when(eventRepository.findById(eventId))
                .thenReturn(Optional.of(event));

        when(eventMapper.toResponse(event))
                .thenReturn(response);


        EventResponse result = eventService.getById(eventId);


        assertEquals(response, result);


        verify(eventRepository)
                .findById(eventId);

        verify(eventMapper)
                .toResponse(event);
    }

    @Test
    void getById_shouldThrowEventNotFoundException() {

        UUID eventId = UUID.randomUUID();


        when(eventRepository.findById(eventId))
                .thenReturn(Optional.empty());


        assertThrows(
                EventNotFoundException.class,
                () -> eventService.getById(eventId)
        );


        verify(eventRepository)
                .findById(eventId);


        verifyNoInteractions(eventMapper);
    }

    @Test
    void update_shouldUpdateEventSuccessfully() {

        UUID eventId = event.getId();


        when(eventRepository.findById(eventId))
                .thenReturn(Optional.of(event));

        when(eventRepository.save(event))
                .thenReturn(event);

        when(eventMapper.toResponse(event))
                .thenReturn(response);


        EventResponse result =
                eventService.update(eventId, updateRequest);


        assertEquals(response, result);


        verify(eventRepository)
                .findById(eventId);

        verify(eventMapper)
                .updateEntity(updateRequest, event);

        verify(eventRepository)
                .save(event);

        verify(eventMapper)
                .toResponse(event);
    }

    @Test
    void update_shouldThrowEventNotFoundException() {

        UUID eventId = UUID.randomUUID();


        when(eventRepository.findById(eventId))
                .thenReturn(Optional.empty());


        assertThrows(
                EventNotFoundException.class,
                () -> eventService.update(eventId, updateRequest)
        );


        verify(eventRepository)
                .findById(eventId);


        verify(eventMapper, never())
                .updateEntity(any(), any());

        verify(eventRepository, never())
                .save(any());
    }

    @Test
    void delete_shouldDeleteEventSuccessfully() {

        UUID eventId = event.getId();


        when(eventRepository.existsById(eventId))
                .thenReturn(true);


        eventService.delete(eventId);


        verify(eventRepository)
                .existsById(eventId);

        verify(eventRepository)
                .deleteById(eventId);
    }

    @Test
    void delete_shouldThrowEventNotFoundException() {

        UUID eventId = UUID.randomUUID();


        when(eventRepository.existsById(eventId))
                .thenReturn(false);


        assertThrows(
                EventNotFoundException.class,
                () -> eventService.delete(eventId)
        );


        verify(eventRepository)
                .existsById(eventId);


        verify(eventRepository, never())
                .deleteById(any());
    }
}