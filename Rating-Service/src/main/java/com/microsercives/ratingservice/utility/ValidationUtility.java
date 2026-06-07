package com.microsercives.ratingservice.utility;

import com.microsercives.ratingservice.dtos.CustomerValidationResponseDTO;
import com.microsercives.ratingservice.dtos.HotelValidationResponseDTO;
import com.microsercives.ratingservice.external.services.hotelservice.HotelService;
import com.microsercives.ratingservice.external.services.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtility {
    @Autowired
    private UserService userService;
    @Autowired
    private HotelService hotelService;

    public Boolean validateCustomer(String customerId){
        CustomerValidationResponseDTO customerValidation = userService.customerValidation(customerId);
        System.out.println("Customer Validation ==> " +  customerValidation.getActive());
        return customerValidation.getActive();
    }
    public Boolean validateHotel(String hotelId){
        HotelValidationResponseDTO hotelValidation = hotelService.hotelValidation(hotelId);
        System.out.println("Hotel Validation ==> " +  hotelValidation.getActive());
        return hotelValidation.getActive();
    }
}
