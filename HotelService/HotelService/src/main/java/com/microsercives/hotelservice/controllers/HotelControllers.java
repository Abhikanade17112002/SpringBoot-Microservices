package com.microsercives.hotelservice.controllers;

import com.microsercives.hotelservice.dtos.CreateHotelRequestDTO;
import com.microsercives.hotelservice.dtos.HotelResponseDTO;
import com.microsercives.hotelservice.dtos.UpdateHotelRequestDTO;
import com.microsercives.hotelservice.entities.Hotel;
import com.microsercives.hotelservice.services.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelControllers {

    private final HotelService hotelService;

    private final ModelMapper modelMapper;

    public HotelControllers(HotelService hotelService,
                            ModelMapper modelMapper) {
        this.hotelService = hotelService;
        this.modelMapper = modelMapper;
    }

    // CREATE HOTEL
    @PostMapping
    public ResponseEntity<HotelResponseDTO> createHotel(
            @RequestBody CreateHotelRequestDTO createHotelRequestDTO
    ) {

        Hotel hotelRequest =
                modelMapper.map(createHotelRequestDTO, Hotel.class);

        Hotel savedHotel = hotelService.createHotel(hotelRequest);

        HotelResponseDTO hotelResponseDTO =
                modelMapper.map(savedHotel, HotelResponseDTO.class);

        return new ResponseEntity<>(hotelResponseDTO, HttpStatus.CREATED);
    }

    // GET ALL HOTELS
    @GetMapping
    public ResponseEntity<List<HotelResponseDTO>> getAllHotels() {

        List<Hotel> hotels = hotelService.getAllHotels();

        List<HotelResponseDTO> hotelResponseDTOS =
                hotels.stream()
                        .map(hotel ->
                                modelMapper.map(hotel, HotelResponseDTO.class))
                        .toList();

        return ResponseEntity.ok(hotelResponseDTOS);
    }

    // GET HOTEL BY ID
    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelResponseDTO> getHotelById(
            @PathVariable String hotelId
    ) {

        Hotel hotel = hotelService.getHotelById(hotelId);

        HotelResponseDTO hotelResponseDTO =
                modelMapper.map(hotel, HotelResponseDTO.class);

        return ResponseEntity.ok(hotelResponseDTO);
    }

    // UPDATE HOTEL
    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelResponseDTO> updateHotel(
            @PathVariable String hotelId,
            @RequestBody UpdateHotelRequestDTO updateHotelRequestDTO
    ) {

        Hotel updatedHotelRequest =
                modelMapper.map(updateHotelRequestDTO, Hotel.class);

        Hotel updatedHotel =
                hotelService.updateHotel(hotelId, updatedHotelRequest);

        HotelResponseDTO hotelResponseDTO =
                modelMapper.map(updatedHotel, HotelResponseDTO.class);

        return ResponseEntity.ok(hotelResponseDTO);
    }

    // DELETE HOTEL
    @DeleteMapping("/{hotelId}")
    public ResponseEntity<String> deleteHotel(
            @PathVariable String hotelId
    ) {

        hotelService.deleteHotel(hotelId);

        return ResponseEntity.ok("Hotel Deleted Successfully");
    }
}