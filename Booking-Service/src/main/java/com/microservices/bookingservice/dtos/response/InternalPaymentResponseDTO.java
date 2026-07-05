package com.microservices.bookingservice.dtos.response;


import com.microservices.bookingservice.enums.PaymentStatus;

public class InternalPaymentResponseDTO {
    private PaymentStatus paymentStatus;
    private String message ;

    public InternalPaymentResponseDTO() {
    }

    public InternalPaymentResponseDTO(PaymentStatus paymentStatus, String message) {
        this.paymentStatus = paymentStatus;
        this.message = message;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "InternalPaymentResponseDTO{" +
                "paymentStatus=" + paymentStatus +
                ", message='" + message + '\'' +
                '}';
    }
}
