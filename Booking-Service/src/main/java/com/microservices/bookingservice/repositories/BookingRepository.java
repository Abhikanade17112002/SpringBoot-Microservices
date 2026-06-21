package com.microservices.bookingservice.repositories;

import com.microservices.bookingservice.entities.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    Page<Booking> findByCustomerId(String userId, Pageable page);
    Page<Booking> findByHotelIdIn(List<String> hotelIds, Pageable pageable);
}
