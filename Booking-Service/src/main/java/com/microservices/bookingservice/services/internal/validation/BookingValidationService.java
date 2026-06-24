package com.microservices.bookingservice.services.internal.validation;

import com.microservices.bookingservice.clinets.hotelclients.InternalHotelService;
import com.microservices.bookingservice.clinets.userclients.InternalUserService;
import com.microservices.bookingservice.dtos.response.ListOfHotelOwnerHotelIdsListResponseDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j
@Service
public class BookingValidationService {
    private final InternalHotelService internalHotelService;
    private final InternalUserService internalUserService;
    public BookingValidationService(InternalHotelService internalHotelService, InternalUserService internalUserService) {
        this.internalHotelService = internalHotelService;
        this.internalUserService = internalUserService;
    }
    @CircuitBreaker( name = "hotel-service-cb", fallbackMethod = "validateHotelIsActiveFallback")
    @Retry(name = "hotel-service-retry")
    public Boolean validateHotelIsActive(String hotelId){
        return internalHotelService.validateHotel(hotelId).getActive();
    }
    public Boolean validateHotelIsActiveFallback(String hotelId,Exception exception){
        log.info("ERROR {} OCCURED AT {}",exception.getMessage(), LocalDateTime.now());
        return false;
    }
    @CircuitBreaker( name = "hotel-service-cb", fallbackMethod = "validateHotelOwnerHotelIdsListFallBackMethode")
    @Retry(name = "hotel-service-retry")
    public ListOfHotelOwnerHotelIdsListResponseDTO validateHotelOwnerHotelIdsList(String HotelOwnerId){
        return internalHotelService.getOwnerHotelsIdList(HotelOwnerId);
    }
    public ListOfHotelOwnerHotelIdsListResponseDTO validateHotelOwnerHotelIdsListFallBackMethode(String HotelOwnerId,Exception exception){
        log.info("ERROR {} OCCURED AT {}",exception.getMessage(), LocalDateTime.now());
        return new ListOfHotelOwnerHotelIdsListResponseDTO(Collections.emptyList());
    }

    @CircuitBreaker( name = "user-service-cb", fallbackMethod = "validateCustomerIsActiveFallback")
    @Retry(name = "user-service-retry")
    public Boolean validateCustomerIsActive(String customerId){
        return internalUserService.validateCustomer(customerId).getActive();
    }
    public Boolean validateCustomerIsActiveFallback(String customerId,Exception exception){
        log.info("ERROR {} OCCURED AT {}",exception.getMessage(), LocalDateTime.now());
        return false;
    }
    @CircuitBreaker( name = "user-service-cb", fallbackMethod = "validateHotelOwnerIsActiveFallback")
    @Retry(name = "user-service-retry")
    public Boolean validateHotelOwnerIsActive( String HotelOwnerId ){
        return internalUserService.validateOwner(HotelOwnerId).getActive();
    }
    public Boolean validateHotelOwnerIsActiveFallback(String HotelOwnerId,Exception exception){
        log.info("ERROR {} OCCURED AT {}",exception.getMessage(), LocalDateTime.now());
        return false;
    }

}
