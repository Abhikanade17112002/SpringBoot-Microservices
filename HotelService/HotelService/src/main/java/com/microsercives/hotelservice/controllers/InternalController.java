package com.microsercives.hotelservice.controllers;

import com.microsercives.hotelservice.dtos.response.HotelValidationResponseDTO;
import com.microsercives.hotelservice.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class InternalController {
    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotels/{hotelId}")
    public HotelValidationResponseDTO validateHotel(@PathVariable("hotelId") String hotelId) {
        return hotelService.validateHotel(hotelId) ;
    }
}
