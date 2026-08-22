package com.microservices.bookingservice.dtos.request;

import com.microservices.bookingservice.enums.PaymentMethode;
import jakarta.validation.constraints.*;

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
    @NotBlank
    @Email(message = "Email Should Be Valid")
    private String customerEmailId;

    public BookingRequestDTO() {
    }

    public BookingRequestDTO(String hotelId, LocalDate checkInDate, LocalDate checkOutDate, BigDecimal totalPrice, boolean active, PaymentMethode paymentMethod, String customerEmailId) {
        this.hotelId = hotelId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.active = active;
        this.paymentMethod = paymentMethod;
        this.customerEmailId = customerEmailId;
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

    public String getCustomerEmailId() {
        return customerEmailId;
    }

    public void setCustomerEmailId(String customerEmailId) {
        this.customerEmailId = customerEmailId;
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
                ", customerEmailId='" + customerEmailId + '\'' +
                '}';
    }
}
