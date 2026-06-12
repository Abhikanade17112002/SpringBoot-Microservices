package com.microsercives.userservice.services;


import com.microsercives.userservice.dtos.request.CustomerSignUpRequestDTO;
import com.microsercives.userservice.dtos.request.HotelOwnerSignUpRequestDTO;
import com.microsercives.userservice.dtos.request.UserSignInRequestDTO;
import com.microsercives.userservice.dtos.response.CustomerSignUpResponseDTO;
import com.microsercives.userservice.dtos.response.HotelOwnerSignUpResponseDTO;
import com.microsercives.userservice.dtos.response.UserSignInResponseDTO;
import com.microsercives.userservice.entities.User;
import org.springframework.http.ResponseEntity;

// Handles SignIn , SignUp For Customer , Admin And Hotel Owner
public interface AuthenticationService {
    CustomerSignUpResponseDTO registerCustomer(CustomerSignUpRequestDTO customerSignUpRequestDTO);
    HotelOwnerSignUpResponseDTO registerHotelOwner(HotelOwnerSignUpRequestDTO hotelOwnerSignUpRequestDTO);
    UserSignInResponseDTO signInUser(UserSignInRequestDTO userSignInRequestDTO);
    User getSavedUserFromRequestDTO( CustomerSignUpRequestDTO customerSignUpRequestDTO , HotelOwnerSignUpRequestDTO hotelOwnerSignUpRequestDTO );

}
