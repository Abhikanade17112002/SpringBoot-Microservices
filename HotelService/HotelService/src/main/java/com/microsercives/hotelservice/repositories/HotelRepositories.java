package com.microsercives.hotelservice.repositories;

import com.microsercives.hotelservice.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepositories extends JpaRepository<Hotel,String> {
}
