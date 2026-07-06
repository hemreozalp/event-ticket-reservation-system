package com.hemreozalp.event_ticket_reservation_system.reservation.mapper;

import com.hemreozalp.event_ticket_reservation_system.reservation.dto.ReservationResponse;
import com.hemreozalp.event_ticket_reservation_system.reservation.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "seatId", source = "seat.id")
    ReservationResponse toResponse(Reservation reservation);

    List<ReservationResponse> toResponseList(List<Reservation> reservations);
}
