package com.hemreozalp.event_ticket_reservation_system.statistics.service;

import com.hemreozalp.event_ticket_reservation_system.event.repository.EventRepository;
import com.hemreozalp.event_ticket_reservation_system.reservation.repository.ReservationRepository;
import com.hemreozalp.event_ticket_reservation_system.seat.entity.SeatStatus;
import com.hemreozalp.event_ticket_reservation_system.seat.repository.SeatRepository;
import com.hemreozalp.event_ticket_reservation_system.statistics.dto.StatisticsResponse;
import com.hemreozalp.event_ticket_reservation_system.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private StatisticsService statisticsService;


    @Test
    void getStatistics_shouldReturnStatistics() {

        when(eventRepository.count())
                .thenReturn(18L);

        when(userRepository.count())
                .thenReturn(120L);

        when(reservationRepository.count())
                .thenReturn(920L);

        when(seatRepository.countByStatus(SeatStatus.RESERVED))
                .thenReturn(710L);


        StatisticsResponse result =
                statisticsService.getStatistics();


        assertEquals(18L, result.totalEvents());
        assertEquals(120L, result.totalUsers());
        assertEquals(920L, result.totalReservations());
        assertEquals(710L, result.occupiedSeats());


        verify(eventRepository)
                .count();

        verify(userRepository)
                .count();

        verify(reservationRepository)
                .count();

        verify(seatRepository)
                .countByStatus(SeatStatus.RESERVED);
    }
}