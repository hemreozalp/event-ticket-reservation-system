package com.hemreozalp.event_ticket_reservation_system.mapper;

import com.hemreozalp.event_ticket_reservation_system.dto.CreateEventRequest;
import com.hemreozalp.event_ticket_reservation_system.dto.EventResponse;
import com.hemreozalp.event_ticket_reservation_system.dto.UpdateEventRequest;
import com.hemreozalp.event_ticket_reservation_system.entity.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    Event toEntity(CreateEventRequest request);
    Event toEntity(UpdateEventRequest request);

    EventResponse toResponse(Event event);
}
