package com.microservices.paymentservice.exception.exceptions;

public class PaymentCannotBeRefundedException extends RuntimeException {

    public PaymentCannotBeRefundedException(String message) {
        super(message);
    }
}