package com.hemreozalp.event_ticket_reservation_system.reservation.entity;

import com.hemreozalp.event_ticket_reservation_system.event.entity.Event;
import com.hemreozalp.event_ticket_reservation_system.seat.entity.Seat;
import com.hemreozalp.event_ticket_reservation_system.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Column(name = "reservation_time", nullable = false)
    private LocalDateTime reservationTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @PrePersist
    protected void onCreate() {
        this.reservationTime = LocalDateTime.now();
    }
}
