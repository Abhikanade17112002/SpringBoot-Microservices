package com.microsercives.ratingservice.services.services.impl;

import com.microsercives.ratingservice.dtos.response.CustomerValidationResponseDTO;
import com.microsercives.ratingservice.dtos.response.HotelValidationResponseDTO;
import com.microsercives.ratingservice.external.services.hotelservice.HotelServiceFeingClient;
import com.microsercives.ratingservice.external.services.userservice.UserServiceFeingClient;
import com.microsercives.ratingservice.services.ValidationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Slf4j
@Component
public class ValidationServiceImpl implements ValidationService {
    @Autowired
    private UserServiceFeingClient userServiceFeingClient;
    @Autowired
    private HotelServiceFeingClient hotelServiceFeingClient;
    private static final Logger logger = LoggerFactory.getLogger(ValidationServiceImpl.class);

    @Retry(name = "userServiceRetry")
    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "userServiceFallBack")
    public Boolean validateCustomer(String customerId){
        CustomerValidationResponseDTO customerValidation = userServiceFeingClient.customerValidation(customerId);
        return customerValidation.getActive();
    }
    public Boolean userServiceFallBack(String customerId,Exception e){
        logger.info("ERROR {} Occured At Time Stamp {}",e.getMessage(), LocalDateTime.now());
        return false;
    }
    @Retry(name = "hotelServiceRetry")
    @CircuitBreaker(name = "createNewRatingCB", fallbackMethod = "hotelServiceFallBack")
    public Boolean validateHotel(String hotelId){
        HotelValidationResponseDTO hotelValidation = hotelServiceFeingClient.hotelValidation(hotelId);
        return hotelValidation.getActive();
    }
    public Boolean hotelServiceFallBack(String hotelId,Exception e){
        logger.info("ERROR {} Occured At Time Stamp {}",e.getMessage(), LocalDateTime.now());
        return false;
    }
}
