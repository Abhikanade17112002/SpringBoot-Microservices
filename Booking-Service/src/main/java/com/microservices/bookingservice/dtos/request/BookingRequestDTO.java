package com.microservices.bookingservice.dtos.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    private double totalPrice;
    private boolean active;

    public BookingRequestDTO() {
    }

    public BookingRequestDTO(String hotelId, LocalDate checkInDate, LocalDate checkOutDate, double totalPrice, boolean active) {
        this.hotelId = hotelId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.active = active;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice( double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public  boolean isActive() {
        return active;
    }

    public void setActive( boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "BookingRequestDTO{" +
                "hotelId='" + hotelId + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", totalPrice=" + totalPrice +
                ", active=" + active +
                '}';
    }
}
