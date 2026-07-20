package com.hemreozalp.event_ticket_reservation_system.common.exception;

import java.util.UUID;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(UUID eventId) {
        super("Event not found with id: " + eventId);
    }
}