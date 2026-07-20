package com.hemreozalp.event_ticket_reservation_system.notification;

import com.hemreozalp.event_ticket_reservation_system.event.entity.Event;
import com.hemreozalp.event_ticket_reservation_system.reservation.entity.Reservation;
import com.hemreozalp.event_ticket_reservation_system.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    public void sendReservationConfirmed(User user, Reservation reservation) {

        log.info("""
                
                ==========================================
                Reservation Confirmed
                
                User         : {}
                Email        : {}
                Reservation  : {}
                Event        : {}
                
                ==========================================
                """,
                user.getUsername(),
                user.getEmail(),
                reservation.getId(),
                reservation.getEvent().getTitle()
        );
    }

    public void sendReservationCancelled(User user, Reservation reservation) {

        log.info("""
                
                ==========================================
                Reservation Cancelled
                
                User         : {}
                Email        : {}
                Reservation  : {}
                Event        : {}
                
                ==========================================
                """,
                user.getUsername(),
                user.getEmail(),
                reservation.getId(),
                reservation.getEvent().getTitle()
        );
    }

    public void sendPaymentFailed(User user, Event event) {

        log.warn("""
                
                ==========================================
                Payment Failed
                
                User  : {}
                Email : {}
                Event : {}
                
                ==========================================
                """,
                user.getUsername(),
                user.getEmail(),
                event.getTitle()
        );
    }

}
