package com.hemreozalp.event_ticket_reservation_system.reservation.service;

import com.hemreozalp.event_ticket_reservation_system.notification.NotificationService;
import com.hemreozalp.event_ticket_reservation_system.payment.entity.Payment;
import com.hemreozalp.event_ticket_reservation_system.payment.entity.PaymentStatus;
import com.hemreozalp.event_ticket_reservation_system.payment.service.PaymentService;
import com.hemreozalp.event_ticket_reservation_system.reservation.dto.ReservationResponse;
import com.hemreozalp.event_ticket_reservation_system.reservation.dto.ReserveSeatRequest;
import com.hemreozalp.event_ticket_reservation_system.reservation.entity.Reservation;
import com.hemreozalp.event_ticket_reservation_system.reservation.entity.ReservationStatus;
import com.hemreozalp.event_ticket_reservation_system.reservation.mapper.ReservationMapper;
import com.hemreozalp.event_ticket_reservation_system.reservation.repository.ReservationRepository;
import com.hemreozalp.event_ticket_reservation_system.seat.entity.Seat;
import com.hemreozalp.event_ticket_reservation_system.seat.entity.SeatStatus;
import com.hemreozalp.event_ticket_reservation_system.seat.repository.SeatRepository;
import com.hemreozalp.event_ticket_reservation_system.user.entity.User;
import com.hemreozalp.event_ticket_reservation_system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final PaymentService paymentService;
    private final NotificationService notificationService;

    public ReservationResponse reserve(ReserveSeatRequest request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Seat seat = seatRepository.findById(request.seatId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.getStatus() != SeatStatus.AVAILABLE) {
            throw new RuntimeException("Seat is not available");
        }

        if (reservationRepository.existsBySeatIdAndStatus(
                seat.getId(),
                ReservationStatus.CONFIRMED
        )) {
            throw new RuntimeException("Seat is already reserved");
        }

        Reservation reservation = Reservation.builder()
                .user(user)
                .event(seat.getEvent())
                .seat(seat)
                .status(ReservationStatus.PENDING)
                .build();

        reservation = reservationRepository.save(reservation);

        Payment payment = paymentService.createPayment(reservation);

        boolean paymentSucceeded = payment.getStatus() == PaymentStatus.SUCCESS;

        if (paymentSucceeded) {
            reservation.setStatus(ReservationStatus.CONFIRMED);
            seat.setStatus(SeatStatus.RESERVED);
        } else {
            reservation.setStatus(ReservationStatus.CANCELLED);
            seat.setStatus(SeatStatus.AVAILABLE);
        }

        reservationRepository.save(reservation);
        seatRepository.save(seat);

        if (paymentSucceeded) {
            notificationService.sendReservationConfirmed(user, reservation);
        } else {
            notificationService.sendPaymentFailed(user, seat.getEvent());
        }

        return reservationMapper.toResponse(reservation);
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> myReservation(UUID userId) {
        return reservationRepository.findByUserId(userId)
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    public void cancel(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setStatus(ReservationStatus.CANCELLED);

        Seat seat = reservation.getSeat();
        seat.setStatus(SeatStatus.AVAILABLE);

        reservationRepository.save(reservation);
        seatRepository.save(seat);

        notificationService.sendReservationCancelled(
                reservation.getUser(),
                reservation
        );
    }
}
