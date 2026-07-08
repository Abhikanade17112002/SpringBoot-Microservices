package com.microservices.bookingservice.repositories;

import com.microservices.bookingservice.entities.Booking;
import com.microservices.bookingservice.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,String> {
    Page<Booking> findByCustomerId(String userId, Pageable page);
    Page<Booking> findByHotelIdIn(List<String> hotelIds, Pageable pageable);
    Page<Booking> findByHotelId(String hotelId, Pageable page);
    Page<Booking> findByHotelIdAndBookingStatus(String hotelId, BookingStatus status, Pageable page);
    Page<Booking> findByHotelIdAndCheckInDateBetween(String hotelId, LocalDate starteDate, LocalDate endDate, Pageable page);
    Optional<Booking> findByHotelIdAndCustomerIdAndCheckInDateAndCheckOutDate(String hotelId, String userId, LocalDate checkInDate, LocalDate checkOutDate);
}
