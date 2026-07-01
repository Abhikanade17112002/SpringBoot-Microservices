package com.microservices.paymentservice.processor.impl;

import com.microservices.paymentservice.dtos.request.CreatePaymentRequestDTO;
import com.microservices.paymentservice.dtos.response.PaymentProcessingResult;
import com.microservices.paymentservice.entities.Payment;
import com.microservices.paymentservice.processor.PaymentProcessor;
import org.springframework.stereotype.Component;


@Component
public class StripePaymentProcessor implements PaymentProcessor {


    @Override
    public PaymentProcessingResult processPayment(Payment request) {
        return null;
    }

    @Override
    public PaymentProcessingResult refundPayment(Payment payment) {
        return null;
    }
}
