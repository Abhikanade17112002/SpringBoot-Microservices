package com.microsercives.hotelservice.services.impl;

import com.microsercives.hotelservice.dtos.request.CreateHotelRequestDTO;
import com.microsercives.hotelservice.dtos.response.HotelResponseDTO;
import com.microsercives.hotelservice.dtos.response.OwnerValidationResponseDTO;
import com.microsercives.hotelservice.external.service.userservice.UserServiceFeingClient;
import com.microsercives.hotelservice.services.HotelOwnerVerificationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class HotelOwnerVerificationServiceImpl implements HotelOwnerVerificationService {
    @Autowired
    private UserServiceFeingClient userServiceFeingClient;
    private Logger logger = LoggerFactory.getLogger(HotelOwnerVerificationServiceImpl.class);

    @Retry(
            name = "userServiceRetry"
    )
    @CircuitBreaker(
            name = "userServiceRetryCB",
            fallbackMethod = "userServiceRetryFallBack"
    )
    public Boolean verifyHotelOwner(String hotelOwnerId) {
        logger.info("HotelOwnerVerificationServiceImpl.verifyHotelOwner()");
        OwnerValidationResponseDTO responseDTO = userServiceFeingClient.validateOwner(hotelOwnerId);
        return responseDTO.getActive();
    }
    public Boolean userServiceRetryFallBack(String hotelOwnerId , Exception e){
        logger.info("ERROR {} Occured At Time Stamp {}",e.getMessage(), LocalDateTime.now());
        return false ;
    }
}
