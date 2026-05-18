package com.microsercives.hotelservice.services;

import com.microsercives.hotelservice.entities.Hotel;

import java.util.List;

public interface HotelService {

    // Create
    Hotel createHotel(Hotel hotel);

    // Get All
    List<Hotel> getAllHotels();

    // Get By Id
    Hotel getHotelById(String hotelId);

    // Update
    Hotel updateHotel(String hotelId, Hotel updatedHotel);

    // Delete
    void deleteHotel(String hotelId);
}