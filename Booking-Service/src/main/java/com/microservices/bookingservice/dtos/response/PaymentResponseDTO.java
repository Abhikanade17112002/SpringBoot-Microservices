package com.microservices.bookingservice.dtos.response;

import com.microservices.bookingservice.enums.PaymentMethode;
import com.microservices.bookingservice.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponseDTO {
    private String paymentId;
    private String bookingId ;
    private String customerId ;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private PaymentMethode paymentMethod;
    private String transactionReference ;
    private LocalDateTime createdAt;
    private String message ;

    public PaymentResponseDTO(String paymentId, String bookingId, String customerId, BigDecimal amount, PaymentStatus paymentStatus, PaymentMethode paymentMethod, String transactionReference, LocalDateTime createdAt , String message) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.transactionReference = transactionReference;
        this.createdAt = createdAt;
        this.message = message ;
    }
    public PaymentResponseDTO() {}

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentMethode getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethode paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    @Override
    public String toString() {
        return "PaymentResponseDTO{" +
                "paymentId='" + paymentId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", amount=" + amount +
                ", paymentStatus=" + paymentStatus +
                ", paymentMethod=" + paymentMethod +
                ", transactionReference='" + transactionReference + '\'' +
                ", createdAt=" + createdAt +
                ", message='" + message + '\'' +
                '}';
    }
}
