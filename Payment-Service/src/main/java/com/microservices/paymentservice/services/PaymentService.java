package com.microservices.paymentservice.services;

import com.microservices.paymentservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.paymentservice.dtos.response.PaymentResponseDTO;
import com.microservices.paymentservice.enums.PaymentMethode;
import com.microservices.paymentservice.enums.PaymentStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface PaymentService {
    PaymentResponseDTO processPayment(@Valid CreatePaymentRequestDTO paymentRequest) throws Exception;
    PaymentResponseDTO getPaymentByBookingId(String bookingId);
    PaymentResponseDTO getPaymentById(String paymentId);
    Page<PaymentResponseDTO> getAllPayments(int pageno, int pagesize, String sortby, Boolean ascending);
    Page<PaymentResponseDTO> getAllCustomerPayments(int pageno, int pagesize, String sortby, Boolean ascending);
    Page<PaymentResponseDTO> getAllPaymentsByStatus(PaymentStatus paymentStatus, int pageno, int pagesize, String sortby, Boolean ascending);
    Page<PaymentResponseDTO> getAllPaymentsByPaymentStatus(PaymentMethode paymentMethode, int pageno, int pagesize, String sortby, Boolean ascending);
    PaymentResponseDTO processBookingPaymentRefund(String bookingId);
}
