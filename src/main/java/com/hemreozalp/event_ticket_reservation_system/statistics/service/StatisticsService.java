package com.hemreozalp.event_ticket_reservation_system.statistics.service;

import com.hemreozalp.event_ticket_reservation_system.event.repository.EventRepository;
import com.hemreozalp.event_ticket_reservation_system.reservation.repository.ReservationRepository;
import com.hemreozalp.event_ticket_reservation_system.seat.entity.SeatStatus;
import com.hemreozalp.event_ticket_reservation_system.seat.repository.SeatRepository;
import com.hemreozalp.event_ticket_reservation_system.statistics.dto.StatisticsResponse;
import com.hemreozalp.event_ticket_reservation_system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;

    public StatisticsResponse getStatistics() {

        long totalEvents = eventRepository.count();
        long totalUsers = userRepository.count();
        long totalReservations = reservationRepository.count();
        long occupiedSeats = seatRepository.countByStatus(SeatStatus.RESERVED);

        return new StatisticsResponse(
                totalEvents,
                totalUsers,
                totalReservations,
                occupiedSeats
        );
    }
}
