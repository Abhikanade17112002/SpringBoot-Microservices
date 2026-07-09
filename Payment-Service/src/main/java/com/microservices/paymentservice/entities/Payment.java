package com.microservices.paymentservice.entities;

import com.microservices.paymentservice.enums.PaymentMethode;
import com.microservices.paymentservice.enums.PaymentStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "payments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "bookingId,paymentId"),
                @UniqueConstraint(columnNames = "transactionReference")
        },
        indexes = {
                @Index(
                        name = "idx_customer",
                        columnList = "customerId"
                ),

                @Index(
                        name = "idx_booking",
                        columnList = "bookingId"
                ),

                @Index(
                        name = "idx_payment_status",
                        columnList = "paymentStatus"
                )}
)
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentId;

    @Column( nullable = false)
    private String bookingId ;
    @Column( nullable = false)
    private String customerId ;
    @Column(
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethode paymentMethod;
    @Column( nullable = true)
    private String paymentGatewayReference ;
    @Column( nullable = false)
    private String transactionReference ;
    @Column( nullable = true)
    private String refundTransactionReference ;
    @Column( nullable = false)
    private Boolean active ;
    @Column( nullable = true)
    private String message ;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public Payment(String paymentId, String bookingId, String customerId, BigDecimal amount, PaymentStatus paymentStatus, PaymentMethode paymentMethod, String paymentGatewayReference, String transactionReference, String refundTransactionReference, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt , String message) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.paymentGatewayReference = paymentGatewayReference;
        this.transactionReference = transactionReference;
        this.refundTransactionReference = refundTransactionReference;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.message = message;
    }


    public Payment() {
    }

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

    public String getPaymentGatewayReference() {
        return paymentGatewayReference;
    }

    public void setPaymentGatewayReference(String paymentGatewayReference) {
        this.paymentGatewayReference = paymentGatewayReference;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getRefundTransactionReference() {
        return refundTransactionReference;
    }

    public void setRefundTransactionReference(String refundTransactionReference) {
        this.refundTransactionReference = refundTransactionReference;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", amount=" + amount +
                ", paymentStatus=" + paymentStatus +
                ", paymentMethod=" + paymentMethod +
                ", paymentGatewayReference='" + paymentGatewayReference + '\'' +
                ", transactionReference='" + transactionReference + '\'' +
                ", refundTransactionReference='" + refundTransactionReference + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
