package com.hemreozalp.event_ticket_reservation_system.seat.mapper;

import com.hemreozalp.event_ticket_reservation_system.seat.dto.SeatResponse;
import com.hemreozalp.event_ticket_reservation_system.seat.entity.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatMapper {

    SeatResponse toResponse(Seat seat);

    List<SeatResponse> toResponseList(List<Seat> seats);
}
