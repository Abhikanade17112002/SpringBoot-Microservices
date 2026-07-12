package com.microservices.paymentservice.repositories;

import com.microservices.paymentservice.entities.Payment;
import com.microservices.paymentservice.enums.PaymentMethode;
import com.microservices.paymentservice.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    Optional<Payment> findByBookingId(
            String bookingId
    );

    Page<Payment> findByCustomerId(
            String customerId,
            Pageable pageable
    );

    Page<Payment> findByPaymentStatus(
            PaymentStatus paymentStatus,
            Pageable pageable
    );

    Page<Payment> findByPaymentMethod(
            PaymentMethode paymentMethod,
            Pageable pageable
    );
    Optional<Payment> findTopByBookingIdOrderByCreatedAtDesc(String bookingId);

    Page<Payment> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable page);

    Optional<Payment>  findByTransactionReference(String transactionReference);

    Optional<Payment> findByPaymentGatewayReference(String gatewayReference);
}
