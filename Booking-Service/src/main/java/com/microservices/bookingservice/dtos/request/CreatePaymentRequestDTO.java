package com.microservices.bookingservice.dtos.request;

import com.microservices.bookingservice.enums.PaymentMethode;

import java.math.BigDecimal;

public class CreatePaymentRequestDTO {

    private String bookingId;

    private String customerId;

    private BigDecimal amount;

    private PaymentMethode paymentMethod;

    public CreatePaymentRequestDTO() {
    }

    public CreatePaymentRequestDTO(String bookingId, String customerId, BigDecimal amount, PaymentMethode paymentMethod) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentMethode getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethode paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "CreatePaymentRequestDTO{" +
                "bookingId='" + bookingId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", amount=" + amount +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
