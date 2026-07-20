package com.hemreozalp.event_ticket_reservation_system.statistics.controller;

import com.hemreozalp.event_ticket_reservation_system.statistics.dto.StatisticsResponse;
import com.hemreozalp.event_ticket_reservation_system.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public StatisticsResponse getStatistics() {
        return statisticsService.getStatistics();
    }
}