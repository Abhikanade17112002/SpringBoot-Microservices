package com.microservices.bookingservice.dtos.request;

import com.microservices.bookingservice.enums.PaymentMethode;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingRequestDTO {
    @NotBlank(message = "Hotel Id Cannot Be Blank")
    private String hotelId;
    @NotNull(message = "Check In Date Should Be Valid")
    @Future(message = "Check In Date Should Be In Future")
    private LocalDate checkInDate;
    @NotNull(message = "Check Out  Date Should Be Valid")
    @Future(message = "Check Out Date Should Be In Future")
    private LocalDate checkOutDate;
    @Positive(message = "Total Price Cannot Be Negative")
    private BigDecimal totalPrice;
    @NotNull(message = "Booking Active Status Cannot Be Null")
    private boolean active;
    @NotNull(message = "Payment Methode Cannot Be Null")
    private PaymentMethode paymentMethod;

    public BookingRequestDTO() {
    }

    public BookingRequestDTO(String hotelId, LocalDate checkInDate, LocalDate checkOutDate, BigDecimal totalPrice, boolean active, PaymentMethode paymentMethod) {
        this.hotelId = hotelId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.active = active;
        this.paymentMethod = paymentMethod;
    }

    public  String getHotelId() {
        return hotelId;
    }

    public void setHotelId( String hotelId) {
        this.hotelId = hotelId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate ( LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate( LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice( BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public  boolean isActive() {
        return active;
    }

    public void setActive( boolean active) {
        this.active = active;
    }

    public PaymentMethode getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethode paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "BookingRequestDTO{" +
                "active=" + active +
                ", paymentMethod=" + paymentMethod +
                ", totalPrice=" + totalPrice +
                ", checkOutDate=" + checkOutDate +
                ", checkInDate=" + checkInDate +
                ", hotelId='" + hotelId + '\'' +
                '}';
    }
}
