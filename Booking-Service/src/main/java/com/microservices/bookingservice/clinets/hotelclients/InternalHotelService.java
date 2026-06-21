package com.microservices.bookingservice.clinets.hotelclients;

import com.microservices.bookingservice.dtos.response.HotelValidationResponseDTO;
import com.microservices.bookingservice.dtos.response.ListOfHotelOwnerHotelIdsListResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( name = "HOTEL-SERVICE-DEV" , path = "/api/v1/internal")
public interface InternalHotelService {
    @GetMapping("/hotels/{hotelId}")
    public HotelValidationResponseDTO validateHotel(@PathVariable("hotelId") String hotelId);
    @GetMapping("/hotels/owners/{ownerId}")
    public ListOfHotelOwnerHotelIdsListResponseDTO getOwnerHotelsIdList(@PathVariable("ownerId") String ownerId) ;
}
