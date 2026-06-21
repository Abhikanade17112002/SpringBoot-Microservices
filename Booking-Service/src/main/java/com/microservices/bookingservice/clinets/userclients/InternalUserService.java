package com.microservices.bookingservice.clinets.userclients;

import com.microservices.bookingservice.dtos.response.CustomerValidationResponseDTO;
import com.microservices.bookingservice.dtos.response.OwnerValidationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE-DEV" , path = "/api/v1/internal")
public interface InternalUserService {
    @GetMapping("/customers/{customerId}")
    public CustomerValidationResponseDTO validateCustomer(@PathVariable("customerId") String customerId);

    @GetMapping("/owners/{ownerId}")
    public OwnerValidationResponseDTO validateOwner(@PathVariable("ownerId") String ownerId);
}
