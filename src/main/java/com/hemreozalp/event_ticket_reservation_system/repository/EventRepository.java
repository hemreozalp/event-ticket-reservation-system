package com.hemreozalp.event_ticket_reservation_system.repository;

import com.hemreozalp.event_ticket_reservation_system.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
}
