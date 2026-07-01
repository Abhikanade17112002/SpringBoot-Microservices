package com.microservices.paymentservice.processor;

import com.microservices.paymentservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.paymentservice.dtos.response.PaymentProcessingResult;
import com.microservices.paymentservice.entities.Payment;

public interface PaymentProcessor {

    PaymentProcessingResult processPayment(Payment request);

    PaymentProcessingResult refundPayment(Payment payment);
}
