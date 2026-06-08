package com.microsercives.ratingservice.external.services.hotelservice;


import com.microsercives.ratingservice.dtos.response.HotelValidationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE-DEV" , path = "/api/v1/internal")
public interface HotelServiceFeingClient {
    @GetMapping("/hotels/{hotelId}")
    public abstract HotelValidationResponseDTO hotelValidation(@PathVariable(name = "hotelId") String hotelId);
}
