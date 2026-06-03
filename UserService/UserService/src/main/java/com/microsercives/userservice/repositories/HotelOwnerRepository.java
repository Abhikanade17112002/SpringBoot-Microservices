package com.microsercives.userservice.repositories;

import com.microsercives.userservice.entities.HotelOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelOwnerRepository extends JpaRepository<HotelOwner,String> {
}
