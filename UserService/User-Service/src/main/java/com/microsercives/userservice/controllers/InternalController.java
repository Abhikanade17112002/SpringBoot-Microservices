package com.microsercives.userservice.controllers;


import com.microsercives.userservice.dtos.response.CustomerValidationResponseDTO;
import com.microsercives.userservice.dtos.response.OwnerValidationResponseDTO;
import com.microsercives.userservice.services.CustomerService;
import com.microsercives.userservice.services.HotelOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class InternalController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private HotelOwnerService hotelOwnerService;

    @GetMapping("/customers/{customerId}")
    public CustomerValidationResponseDTO validateCustomer(@PathVariable("customerId") String customerId) {
        return customerService.validateCustomer(customerId) ;
    }
    @GetMapping("/owners/{ownerId}")
    public OwnerValidationResponseDTO validateOwner(@PathVariable("ownerId") String ownerId) {
        return hotelOwnerService.validateOwner(ownerId) ;
    }
}
