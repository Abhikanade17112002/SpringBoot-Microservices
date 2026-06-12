package com.microsercives.hotelservice.external.service.userservice;

import com.microsercives.hotelservice.dtos.response.OwnerValidationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE-DEV" , path = "/api/v1/internal")
public interface UserServiceFeingClient {
    @GetMapping("/owners/{ownerId}")
    public abstract OwnerValidationResponseDTO validateOwner(@PathVariable("ownerId") String ownerId);
}
