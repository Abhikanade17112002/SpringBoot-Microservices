package com.microservices.bookingservice.entities;
import com.microservices.bookingservice.enums.BookingStatus;
import com.microservices.bookingservice.enums.PaymentMethode;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "bookings",
        indexes = {
                @Index(
                        name = "idx_status_expirytime",
                        columnList = "bookingStatus,paymentExpiryTime"
                )
        }
)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String bookingId;
    @Column( nullable = false)
    private String customerId;
    @Column(nullable = false)
    private String hotelId;
    @Column(nullable = false)
    private LocalDate checkInDate;
    @Column(nullable = false)
    private LocalDate checkOutDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private boolean active;
    private Integer paymentAttemptCount  = 0;
    private LocalDateTime paymentExpiryTime ;
    private PaymentMethode paymentMethod;

    public Booking(){
    }

    public Booking(String bookingId, String customerId, String hotelId, LocalDate checkInDate, LocalDate checkOutDate, BookingStatus bookingStatus, BigDecimal totalPrice, LocalDateTime createdAt, LocalDateTime updatedAt, boolean active, Integer paymentAttemptCount, LocalDateTime paymentExpiryTime, PaymentMethode paymentMethod) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.hotelId = hotelId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingStatus = bookingStatus;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
        this.paymentAttemptCount = paymentAttemptCount;
        this.paymentExpiryTime = paymentExpiryTime;
        this.paymentMethod = paymentMethod;
    }

    public PaymentMethode getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethode paymentMethod) {
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

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getPaymentAttemptCount() {
        return paymentAttemptCount;
    }

    public void setPaymentAttemptCount(Integer paymentRetryCount) {
        this.paymentAttemptCount = paymentRetryCount;
    }

    public LocalDateTime getPaymentExpiryTime() {
        return paymentExpiryTime;
    }

    public void setPaymentExpiryTime(LocalDateTime paymentExpiryTime) {
        this.paymentExpiryTime = paymentExpiryTime;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", bookingStatus=" + bookingStatus +
                ", totalPrice=" + totalPrice +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", active=" + active +
                ", paymentAttemptCount=" + paymentAttemptCount +
                ", paymentExpiryTime=" + paymentExpiryTime +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
