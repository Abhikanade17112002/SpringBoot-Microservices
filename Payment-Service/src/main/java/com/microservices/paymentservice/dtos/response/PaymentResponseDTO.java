package com.microservices.paymentservice.dtos.response;

import com.microservices.paymentservice.enums.PaymentMethode;
import com.microservices.paymentservice.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponseDTO {
    private String paymentId;
    private String bookingId ;
    private String customerId ;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private PaymentMethode paymentMethode;
    private String transactionReference ;
    private LocalDateTime createdAt;

    public PaymentResponseDTO(String paymentId, String bookingId, String customerId, BigDecimal amount, PaymentStatus paymentStatus, PaymentMethode paymentMethode, String transactionReference, LocalDateTime createdAt) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentMethode = paymentMethode;
        this.transactionReference = transactionReference;
        this.createdAt = createdAt;
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

    public PaymentMethode getPaymentMethode() {
        return paymentMethode;
    }

    public void setPaymentMethode(PaymentMethode paymentMethode) {
        this.paymentMethode = paymentMethode;
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

    @Override
    public String toString() {
        return "PaymentResponseDTO{" +
                "paymentId='" + paymentId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", amount=" + amount +
                ", paymentStatus=" + paymentStatus +
                ", paymentMethode=" + paymentMethode +
                ", transactionReference='" + transactionReference + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
