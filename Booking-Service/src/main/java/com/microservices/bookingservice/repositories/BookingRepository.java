package com.microservices.bookingservice.repositories;

import com.microservices.bookingservice.entities.Booking;
import com.microservices.bookingservice.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,String> {

    public static final String findByBookingStatusAndPaymentExpiryTimeBeforeQuery = """
    SELECT b
    FROM Booking b
    WHERE b.bookingStatus = :bookingStatus
    AND b.paymentExpiryTime < :paymentExpiryTime""";

    Page<Booking> findByCustomerId(String userId, Pageable page);
    Page<Booking> findByHotelIdIn(List<String> hotelIds, Pageable pageable);
    Page<Booking> findByHotelId(String hotelId, Pageable page);
    Page<Booking> findByHotelIdAndBookingStatus(String hotelId, BookingStatus status, Pageable page);
    Page<Booking> findByHotelIdAndCheckInDateBetween(String hotelId, LocalDate starteDate, LocalDate endDate, Pageable page);
    Optional<Booking> findByHotelIdAndCustomerIdAndCheckInDateAndCheckOutDateAndBookingStatusNotIn(String hotelId, String userId, LocalDate checkInDate, LocalDate checkOutDate, List<BookingStatus> excludeBookingStatus);
    Page<Booking> findAllByBookingStatus(BookingStatus status, Pageable page);
    @Query(findByBookingStatusAndPaymentExpiryTimeBeforeQuery)
    List<Booking> findByBookingStatusAndPaymentExpiryTimeBefore(@Param("bookingStatus") BookingStatus bookingStatus, @Param("paymentExpiryTime") LocalDateTime paymentExpiryTime);
}
