package com.microservices.bookingservice.dtos.response;

import com.microservices.bookingservice.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingRefundResponseDTO {
    private String bookingId;
    private String customerId;
    private String hotelId;
    private BookingStatus bookingStatus;
    private BigDecimal totalPrice;
    private boolean active;
    private String message ;

    public BookingRefundResponseDTO(String bookingId, String customerId, String hotelId, BookingStatus bookingStatus, BigDecimal totalPrice, boolean active, String message) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.hotelId = hotelId;
        this.bookingStatus = bookingStatus;
        this.totalPrice = totalPrice;
        this.active = active;
        this.message = message;
    }

    public BookingRefundResponseDTO() {}

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

    @Override
    public String toString() {
        return "BookingRefundResponseDTO{" +
                "bookingId='" + bookingId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", bookingStatus=" + bookingStatus +
                ", totalPrice=" + totalPrice +
                ", active=" + active +
                ", message='" + message + '\'' +
                '}';
    }
}
