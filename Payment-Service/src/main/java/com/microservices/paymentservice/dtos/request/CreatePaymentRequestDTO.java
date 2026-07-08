package com.microservices.paymentservice.dtos.request;

import com.microservices.paymentservice.enums.PaymentMethode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreatePaymentRequestDTO {
    @NotEmpty(message = "Booking Id Cannot Be Empty")
    private String bookingId;
    @NotEmpty(message = "Customer Id Cannot Be Empty")
    private String customerId;
    @DecimalMin("0.0")
    private BigDecimal amount;
    @NotNull( message = "Payment Methode Cannot Be Null")
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
