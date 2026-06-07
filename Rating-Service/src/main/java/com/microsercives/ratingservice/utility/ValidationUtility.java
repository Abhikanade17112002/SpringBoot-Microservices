package com.microsercives.ratingservice.utility;

import com.microsercives.ratingservice.dtos.CustomerValidationResponseDTO;
import com.microsercives.ratingservice.dtos.HotelValidationResponseDTO;
import com.microsercives.ratingservice.external.services.hotelservice.HotelServiceFeingClient;
import com.microsercives.ratingservice.external.services.userservice.UserServiceFeingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtility {
    @Autowired
    private UserServiceFeingClient userServiceFeingClient;
    @Autowired
    private HotelServiceFeingClient hotelServiceFeingClient;

    public Boolean validateCustomer(String customerId){
        CustomerValidationResponseDTO customerValidation = userServiceFeingClient.customerValidation(customerId);
        System.out.println("Customer Validation ==> " +  customerValidation.getActive());
        return customerValidation.getActive();
    }
    public Boolean validateHotel(String hotelId){
        HotelValidationResponseDTO hotelValidation = hotelServiceFeingClient.hotelValidation(hotelId);
        System.out.println("Hotel Validation ==> " +  hotelValidation.getActive());
        return hotelValidation.getActive();
    }
}
