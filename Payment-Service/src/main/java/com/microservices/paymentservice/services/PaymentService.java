package com.microservices.paymentservice.services;

import com.microservices.paymentservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.paymentservice.dtos.request.PaymentRefundRequestDTO;
import com.microservices.paymentservice.dtos.response.PaymentResponseDTO;
import jakarta.validation.Valid;

public interface PaymentService {
    PaymentResponseDTO processPayment(@Valid CreatePaymentRequestDTO paymentRequest) throws Exception;

    PaymentResponseDTO refundPayment(@Valid PaymentRefundRequestDTO paymentRefundRequest);
}
