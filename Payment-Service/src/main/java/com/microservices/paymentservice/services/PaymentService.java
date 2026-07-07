package com.microservices.paymentservice.services;

import com.microservices.paymentservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.paymentservice.dtos.response.PaymentResponseDTO;
import com.microservices.paymentservice.entities.AuthenticatedUser;
import com.microservices.paymentservice.entities.Payment;
import com.microservices.paymentservice.enums.PaymentMethode;
import com.microservices.paymentservice.enums.PaymentStatus;
import com.microservices.paymentservice.exception.exceptions.PaymentNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;

public interface PaymentService {
    PaymentResponseDTO processPayment(@Valid CreatePaymentRequestDTO paymentRequest) throws Exception;
    PaymentResponseDTO getPaymentByBookingId(String bookingId);
    PaymentResponseDTO getPaymentById(String paymentId);
    Page<PaymentResponseDTO> getAllPayments(int pageno, int pagesize, String sortby, Boolean ascending);
    Page<PaymentResponseDTO> getAllCustomerPayments(int pageno, int pagesize, String sortby, Boolean ascending);
    Page<PaymentResponseDTO> getAllPaymentsByStatus(PaymentStatus paymentStatus, int pageno, int pagesize, String sortby, Boolean ascending);
    Page<PaymentResponseDTO> getAllPaymentsByPaymentStatus(PaymentMethode paymentMethode, int pageno, int pagesize, String sortby, Boolean ascending);
    PaymentResponseDTO retryPaymentByBookingId(String bookingId);
    PaymentResponseDTO refundPaymentWithBookingId(String bookingId);
    PaymentResponseDTO getPaymentByBookingIdForAdminOrCustomer(String bookingId);
    Page<PaymentResponseDTO> getPaymentsBetweenDates(LocalDate start, LocalDate end, int pageno, int size, String sortby, Boolean ascending);
    PaymentResponseDTO getPaymentByTransactionReference(String transactionReference);
    public PaymentResponseDTO getPaymentByGatewayReference(String gatewayReference);

}
