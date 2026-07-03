package com.hemreozalp.event_ticket_reservation_system.seat.repository;

import com.hemreozalp.event_ticket_reservation_system.event.entity.Event;
import com.hemreozalp.event_ticket_reservation_system.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByEventId(UUID eventId);
}
