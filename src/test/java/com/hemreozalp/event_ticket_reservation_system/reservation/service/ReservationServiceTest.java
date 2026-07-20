package com.hemreozalp.event_ticket_reservation_system.reservation.service;

import com.hemreozalp.event_ticket_reservation_system.common.exception.*;
import com.hemreozalp.event_ticket_reservation_system.event.entity.Event;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ReservationService reservationService;

    private User user;
    private Seat seat;
    private Event event;
    private Reservation reservation;
    private ReserveSeatRequest request;
    private Payment payment;
    private ReservationResponse response;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .id(UUID.randomUUID())
                .username("emre")
                .build();

        event = Event.builder()
                .id(UUID.randomUUID())
                .title("Rock Concert")
                .build();

        seat = Seat.builder()
                .id(1L)
                .status(SeatStatus.AVAILABLE)
                .event(event)
                .build();

        reservation = Reservation.builder()
                .user(user)
                .seat(seat)
                .status(ReservationStatus.PENDING)
                .build();

        request = new ReserveSeatRequest(
                user.getId(),
                seat.getId()
        );

        payment = Payment.builder()
                .status(PaymentStatus.SUCCESS)
                .build();

        response = new ReservationResponse(
                UUID.randomUUID(),
                null,
                seat.getId(),
                ReservationStatus.CONFIRMED,
                LocalDateTime.now()
        );
    }

    @Test
    void reserve_shouldReserveSeatSuccessfully() {

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        when(seatRepository.findById(seat.getId()))
                .thenReturn(Optional.of(seat));

        when(reservationRepository.existsBySeatIdAndStatus(
                seat.getId(),
                ReservationStatus.CONFIRMED))
                .thenReturn(false);

        when(reservationRepository.save(any(Reservation.class)))
                .thenReturn(reservation);

        when(paymentService.createPayment(any(Reservation.class)))
                .thenReturn(payment);

        when(reservationMapper.toResponse(any(Reservation.class)))
                .thenReturn(response);

        ReservationResponse result = reservationService.reserve(request);

        assertEquals(response, result);
        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
        assertEquals(SeatStatus.RESERVED, seat.getStatus());

        verify(notificationService)
                .sendReservationConfirmed(user, reservation);

        verify(notificationService, never())
                .sendPaymentFailed(any(), any());
    }

    @Test
    void reserve_shouldThrowUserNotFoundException() {

        when(userRepository.findById(request.userId()))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> reservationService.reserve(request)
        );

        verify(userRepository).findById(request.userId());

        verifyNoInteractions(
                seatRepository,
                reservationRepository,
                paymentService,
                notificationService
        );
    }

    @Test
    void reserve_shouldThrowSeatNotFoundException() {

        when(userRepository.findById(request.userId()))
                .thenReturn(Optional.of(user));

        when(seatRepository.findById(request.seatId()))
                .thenReturn(Optional.empty());

        assertThrows(
                SeatNotFoundException.class,
                () -> reservationService.reserve(request)
        );

        verify(userRepository)
                .findById(request.userId());

        verify(seatRepository)
                .findById(request.seatId());

        verifyNoInteractions(
                paymentService,
                notificationService
        );
    }

    @Test
    void reserve_shouldThrowSeatNotAvailableException() {

        seat.setStatus(SeatStatus.RESERVED);

        when(userRepository.findById(request.userId()))
                .thenReturn(Optional.of(user));

        when(seatRepository.findById(request.seatId()))
                .thenReturn(Optional.of(seat));

        assertThrows(
                SeatNotAvailableException.class,
                () -> reservationService.reserve(request)
        );

        verify(seatRepository)
                .findById(request.seatId());

        verifyNoInteractions(
                paymentService,
                notificationService
        );

        verify(reservationRepository, never())
                .save(any());
    }

    @Test
    void reserve_shouldThrowSeatAlreadyReservedException() {

        when(userRepository.findById(request.userId()))
                .thenReturn(Optional.of(user));

        when(seatRepository.findById(request.seatId()))
                .thenReturn(Optional.of(seat));

        when(reservationRepository.existsBySeatIdAndStatus(
                seat.getId(),
                ReservationStatus.CONFIRMED))
                .thenReturn(true);

        assertThrows(
                SeatAlreadyReservedException.class,
                () -> reservationService.reserve(request)
        );

        verify(reservationRepository)
                .existsBySeatIdAndStatus(
                        seat.getId(),
                        ReservationStatus.CONFIRMED
                );

        verify(paymentService, never())
                .createPayment(any());

        verify(notificationService, never())
                .sendReservationConfirmed(any(), any());
    }

    @Test
    void reserve_shouldCancelReservationWhenPaymentFails() {

        payment.setStatus(PaymentStatus.FAILED);

        when(userRepository.findById(request.userId()))
                .thenReturn(Optional.of(user));

        when(seatRepository.findById(request.seatId()))
                .thenReturn(Optional.of(seat));

        when(reservationRepository.existsBySeatIdAndStatus(
                seat.getId(),
                ReservationStatus.CONFIRMED))
                .thenReturn(false);

        when(reservationRepository.save(any(Reservation.class)))
                .thenReturn(reservation);

        when(paymentService.createPayment(any(Reservation.class)))
                .thenReturn(payment);

        when(reservationMapper.toResponse(any(Reservation.class)))
                .thenReturn(response);


        ReservationResponse result = reservationService.reserve(request);


        assertEquals(response, result);
        assertEquals(
                ReservationStatus.CANCELLED,
                reservation.getStatus()
        );

        assertEquals(
                SeatStatus.AVAILABLE,
                seat.getStatus()
        );


        verify(notificationService)
                .sendPaymentFailed(
                        user,
                        seat.getEvent()
                );

        verify(notificationService, never())
                .sendReservationConfirmed(any(), any());
    }

    @Test
    void myReservation_shouldReturnReservations() {

        UUID userId = user.getId();

        ReservationResponse reservationResponse = new ReservationResponse(
                UUID.randomUUID(),
                null,
                seat.getId(),
                ReservationStatus.CONFIRMED,
                LocalDateTime.now()
        );

        when(reservationRepository.findByUserId(userId))
                .thenReturn(List.of(reservation));

        when(reservationMapper.toResponse(reservation))
                .thenReturn(reservationResponse);


        List<ReservationResponse> result =
                reservationService.myReservation(userId);


        assertEquals(1, result.size());
        assertEquals(reservationResponse, result.get(0));

        verify(reservationRepository)
                .findByUserId(userId);

        verify(reservationMapper)
                .toResponse(reservation);
    }

    @Test
    void cancel_shouldCancelReservation() {

        UUID reservationId = UUID.randomUUID();

        reservation.setStatus(ReservationStatus.CONFIRMED);
        seat.setStatus(SeatStatus.RESERVED);


        when(reservationRepository.findById(reservationId))
                .thenReturn(Optional.of(reservation));


        reservationService.cancel(reservationId);


        assertEquals(
                ReservationStatus.CANCELLED,
                reservation.getStatus()
        );

        assertEquals(
                SeatStatus.AVAILABLE,
                seat.getStatus()
        );


        verify(reservationRepository)
                .save(reservation);

        verify(seatRepository)
                .save(seat);

        verify(notificationService)
                .sendReservationCancelled(
                        user,
                        reservation
                );
    }

    @Test
    void cancel_shouldThrowReservationNotFoundException() {

        UUID reservationId = UUID.randomUUID();

        when(reservationRepository.findById(reservationId))
                .thenReturn(Optional.empty());


        assertThrows(
                ReservationNotFoundException.class,
                () -> reservationService.cancel(reservationId)
        );


        verify(reservationRepository)
                .findById(reservationId);

        verifyNoInteractions(
                seatRepository,
                notificationService
        );
    }

    @Test
    void cancel_shouldThrowIllegalStateExceptionWhenAlreadyCancelled() {

        UUID reservationId = UUID.randomUUID();

        reservation.setStatus(ReservationStatus.CANCELLED);


        when(reservationRepository.findById(reservationId))
                .thenReturn(Optional.of(reservation));


        assertThrows(
                IllegalStateException.class,
                () -> reservationService.cancel(reservationId)
        );


        verify(reservationRepository)
                .findById(reservationId);

        verify(reservationRepository, never())
                .save(any());

        verify(seatRepository, never())
                .save(any());

        verifyNoInteractions(notificationService);
    }
}