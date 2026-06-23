package com.microservices.bookingservice.services.internal.validation;

import com.microservices.bookingservice.clinets.hotelclients.InternalHotelService;
import com.microservices.bookingservice.clinets.userclients.InternalUserService;
import com.microservices.bookingservice.dtos.response.ListOfHotelOwnerHotelIdsListResponseDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    @CircuitBreaker( name = "hotel-service-cb")
    @Retry(name = "hotel-service-retry", fallbackMethod = "validateHotelIsActiveFallback")
    public Boolean validateHotelIsActive(String hotelId){
        return internalHotelService.validateHotel(hotelId).getActive();
    }
    public Boolean validateHotelIsActiveFallback(String hotelId,Exception exception){
        log.error(exception.getMessage(), exception);
        return false;
    }
    @CircuitBreaker( name = "hotel-service-cb")
    @Retry(name = "hotel-service-retry", fallbackMethod = "validateHotelOwnerHotelIdsListFallBackMethode")
    public ListOfHotelOwnerHotelIdsListResponseDTO validateHotelOwnerHotelIdsList(String HotelOwnerId){
        return internalHotelService.getOwnerHotelsIdList(HotelOwnerId);
    }
    public ListOfHotelOwnerHotelIdsListResponseDTO validateHotelOwnerHotelIdsListFallBackMethode(String HotelOwnerId,Exception exception){
        log.error(exception.getMessage(), exception);
        return new ListOfHotelOwnerHotelIdsListResponseDTO(Collections.emptyList());
    }

    @CircuitBreaker( name = "user-service-cb")
    @Retry(name = "user-service-retry", fallbackMethod = "validateCustomerIsActiveFallback")
    public Boolean validateCustomerIsActive(String customerId){
        return internalUserService.validateCustomer(customerId).getActive();
    }
    public Boolean validateCustomerIsActiveFallback(String customerId,Exception exception){
        log.error(exception.getMessage(), exception);
        return false;
    }
    @CircuitBreaker( name = "user-service-cb")
    @Retry(name = "user-service-retry", fallbackMethod = "validateHotelOwnerIsActiveFallback")
    public Boolean validateHotelOwnerIsActive( String HotelOwnerId ){
        return internalUserService.validateOwner(HotelOwnerId).getActive();
    }
    public Boolean validateHotelOwnerIsActiveFallback(String HotelOwnerId,Exception exception){
        log.error(exception.getMessage(), exception);
        return false;
    }

}
