package com.hemreozalp.event_ticket_reservation_system.reservation.repository;

import com.hemreozalp.event_ticket_reservation_system.reservation.entity.Reservation;
import com.hemreozalp.event_ticket_reservation_system.reservation.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findByUserId(UUID userId);

    boolean existsBySeatIdAndStatus(Long seatId, ReservationStatus status);
}
