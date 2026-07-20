package com.hemreozalp.event_ticket_reservation_system.payment.repository;

import com.hemreozalp.event_ticket_reservation_system.payment.entity.Payment;
import com.hemreozalp.event_ticket_reservation_system.payment.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByReservationId(UUID reservationId);
    List<Payment> findAllByStatus(PaymentStatus status);
}
