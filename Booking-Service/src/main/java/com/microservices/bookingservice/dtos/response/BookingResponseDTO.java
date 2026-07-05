package com.microservices.bookingservice.dtos.response;

import com.microservices.bookingservice.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingResponseDTO {
    private String bookingId;
    private String customerId;
    private String hotelId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BookingStatus bookingStatus;
    private BigDecimal totalPrice;
    private boolean active;
    private String message ;
    private Boolean retryAllowed;
    private LocalDateTime paymentExpiryTime;

    public BookingResponseDTO() {

    }

    public BookingResponseDTO(String bookingId, String customerId, String hotelId, LocalDate checkInDate, LocalDate checkOutDate, BookingStatus bookingStatus, BigDecimal totalPrice, boolean active, String message, Boolean retryAllowed, LocalDateTime paymentExpiryTime) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.hotelId = hotelId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingStatus = bookingStatus;
        this.totalPrice = totalPrice;
        this.active = active;
        this.message = message;
        this.retryAllowed = retryAllowed;
        this.paymentExpiryTime = paymentExpiryTime;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getRetryAllowed() {
        return retryAllowed;
    }

    public void setRetryAllowed(Boolean retryAllowed) {
        this.retryAllowed = retryAllowed;
    }

    public LocalDateTime getPaymentExpiryTime() {
        return paymentExpiryTime;
    }

    public void setPaymentExpiryTime(LocalDateTime paymentExpiryTime) {
        this.paymentExpiryTime = paymentExpiryTime;
    }

    @Override
    public String toString() {
        return "BookingResponseDTO{" +
                "bookingId='" + bookingId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", bookingStatus=" + bookingStatus +
                ", totalPrice=" + totalPrice +
                ", active=" + active +
                ", message='" + message + '\'' +
                ", retryAllowed=" + retryAllowed +
                ", paymentExpiryTime=" + paymentExpiryTime +
                '}';
    }
}
