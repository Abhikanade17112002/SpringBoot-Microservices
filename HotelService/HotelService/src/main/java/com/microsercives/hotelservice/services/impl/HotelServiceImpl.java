package com.microsercives.hotelservice.services.impl;

import com.microsercives.hotelservice.entities.Hotel;
import com.microsercives.hotelservice.repositories.HotelRepositories;
import com.microsercives.hotelservice.services.HotelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepositories hotelRepositories;

    public HotelServiceImpl(HotelRepositories hotelRepositories) {
        this.hotelRepositories = hotelRepositories;
    }

    // CREATE
    @Override
    public Hotel createHotel(Hotel hotel) {

        return hotelRepositories.save(hotel);
    }

    // GET ALL
    @Override
    public List<Hotel> getAllHotels() {

        return hotelRepositories.findAll();
    }

    // GET BY ID
    @Override
    public Hotel getHotelById(String hotelId) {

        return hotelRepositories.findById(hotelId)
                .orElseThrow(() ->
                        new RuntimeException("Hotel Not Found With ID : " + hotelId));
    }

    // UPDATE
    @Override
    public Hotel updateHotel(String hotelId, Hotel updatedHotel) {

        Hotel existingHotel = hotelRepositories.findById(hotelId)
                .orElseThrow(() ->
                        new RuntimeException("Hotel Not Found With ID : " + hotelId));

        existingHotel.setHotelName(updatedHotel.getHotelName());
        existingHotel.setLocation(updatedHotel.getLocation());
        existingHotel.setDescription(updatedHotel.getDescription());

        return hotelRepositories.save(existingHotel);
    }

    // DELETE
    @Override
    public void deleteHotel(String hotelId) {

        Hotel existingHotel = hotelRepositories.findById(hotelId)
                .orElseThrow(() ->
                        new RuntimeException("Hotel Not Found With ID : " + hotelId));

        hotelRepositories.delete(existingHotel);
    }
}