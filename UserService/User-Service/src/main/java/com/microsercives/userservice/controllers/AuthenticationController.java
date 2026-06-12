package com.microsercives.userservice.controllers;


import com.microsercives.userservice.dtos.request.CustomerSignUpRequestDTO;
import com.microsercives.userservice.dtos.request.HotelOwnerSignUpRequestDTO;
import com.microsercives.userservice.dtos.request.UserSignInRequestDTO;
import com.microsercives.userservice.dtos.response.CustomerSignUpResponseDTO;
import com.microsercives.userservice.dtos.response.HotelOwnerSignUpResponseDTO;
import com.microsercives.userservice.dtos.response.UserSignInResponseDTO;
import com.microsercives.userservice.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService ;
    @GetMapping
    public ResponseEntity<String> getDefaultPath(){
        return ResponseEntity.ok("Ok") ;
    }

    @PostMapping("/onboardcustomer")
    public ResponseEntity<CustomerSignUpResponseDTO> onBoardCustomer(@RequestBody CustomerSignUpRequestDTO customerSignUpRequestDTO){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authenticationService.registerCustomer(customerSignUpRequestDTO));

    }

    @PostMapping("/onboardowner")
    public ResponseEntity<HotelOwnerSignUpResponseDTO> onBoardHotelOwner(@RequestBody HotelOwnerSignUpRequestDTO hotelOwnerSignUpRequestDTO ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authenticationService.registerHotelOwner(hotelOwnerSignUpRequestDTO));
    }

    @PostMapping("/usersignin")
    public ResponseEntity<UserSignInResponseDTO> userSignIn(@RequestBody UserSignInRequestDTO userSignInRequestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(authenticationService.signInUser(userSignInRequestDTO));
    }
}
