package com.hemreozalp.event_ticket_reservation_system.mapper;

import com.hemreozalp.event_ticket_reservation_system.dto.CreateEventRequest;
import com.hemreozalp.event_ticket_reservation_system.dto.EventResponse;
import com.hemreozalp.event_ticket_reservation_system.dto.UpdateEventRequest;
import com.hemreozalp.event_ticket_reservation_system.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Event toEntity(CreateEventRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UpdateEventRequest request, @MappingTarget Event event);

    EventResponse toResponse(Event event);
}
