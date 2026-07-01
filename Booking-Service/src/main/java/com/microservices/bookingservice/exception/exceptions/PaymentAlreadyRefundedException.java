package com.microservices.bookingservice.exception.exceptions;

public class PaymentAlreadyRefundedException extends RuntimeException {

    public PaymentAlreadyRefundedException(String message) {
        super(message);
    }
}
