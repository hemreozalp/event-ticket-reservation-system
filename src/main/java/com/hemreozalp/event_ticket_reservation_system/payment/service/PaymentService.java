package com.hemreozalp.event_ticket_reservation_system.payment.service;

import com.hemreozalp.event_ticket_reservation_system.payment.entity.Payment;
import com.hemreozalp.event_ticket_reservation_system.payment.entity.PaymentStatus;
import com.hemreozalp.event_ticket_reservation_system.payment.repository.PaymentRepository;
import com.hemreozalp.event_ticket_reservation_system.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment createPayment(Reservation reservation) {

        Payment payment = Payment.builder()
                .reservation(reservation)
                .amount(reservation.getEvent().getPrice())
                .status(PaymentStatus.PENDING)
                .build();

        PaymentStatus result = ThreadLocalRandom.current().nextInt(100) < 80
                ? PaymentStatus.SUCCESS
                : PaymentStatus.FAILED;

        payment.setStatus(result);

        return paymentRepository.save(payment);
    }

}
