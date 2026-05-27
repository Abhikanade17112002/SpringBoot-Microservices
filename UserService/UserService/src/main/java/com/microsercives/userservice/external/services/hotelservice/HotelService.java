package com.microsercives.userservice.external.services.hotelservice;


import com.microsercives.userservice.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( name = "HOTEL-SERVICE" , path = "/api/v1")
public interface HotelService {
    @GetMapping("/hotels/{hotelId}")
    ResponseEntity<Hotel> getHotelById(@PathVariable(name = "hotelId") String hotelId) ;
}
