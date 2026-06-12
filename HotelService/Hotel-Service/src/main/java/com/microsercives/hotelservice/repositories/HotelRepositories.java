package com.microsercives.hotelservice.repositories;

import com.microsercives.hotelservice.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepositories extends JpaRepository<Hotel,String> {
    Page<Hotel> findByHotelNameContainingIgnoreCase(String hotelName, Pageable pageable);

    Page<Hotel> findByLocationContainingIgnoreCase(String location, Pageable pageable);

    Page<Hotel> findByOwnerId(String ownerId, Pageable pageable);
}
