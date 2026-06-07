package com.microsercives.ratingservice.external.services.userservice;

import com.microsercives.ratingservice.dtos.CustomerValidationResponseDTO;
import jakarta.ws.rs.GET;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE-DEV",path = "/api/v1/internal")
public interface UserServiceFeingClient {
    @GetMapping("/customers/{customerId}")
    public abstract CustomerValidationResponseDTO customerValidation(@PathVariable(name = "customerId") String customerId);
}
