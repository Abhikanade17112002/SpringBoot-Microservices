package com.microservices.bookingservice.exception.exceptions;

public class PaymentCannotBeRefundedException extends RuntimeException {

    public PaymentCannotBeRefundedException(String message) {
        super(message);
    }
}