package com.microservices.bookingservice.services.internal.validation;

import com.microservices.bookingservice.clinets.hotelclients.InternalHotelService;
import com.microservices.bookingservice.clinets.userclients.InternalUserService;
import com.microservices.bookingservice.dtos.response.ListOfHotelOwnerHotelIdsListResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class BookingValidationService {
    private final InternalHotelService internalHotelService;
    private final InternalUserService internalUserService;
    public BookingValidationService(InternalHotelService internalHotelService, InternalUserService internalUserService) {
        this.internalHotelService = internalHotelService;
        this.internalUserService = internalUserService;
    }

    public Boolean validateHotelIsActive(String hotelId){
        return internalHotelService.validateHotel(hotelId).getActive();
    }

    public Boolean validateCustomerIsActive(String customerId){
        return internalUserService.validateCustomer(customerId).getActive();
    }

    public Boolean validateHotelOwnerIsActive( String HotelOwnerId ){
        return internalUserService.validateOwner(HotelOwnerId).getActive();
    }

    public ListOfHotelOwnerHotelIdsListResponseDTO validateHotelOwnerHotelIdsList(String HotelOwnerId){
        return internalHotelService.getOwnerHotelsIdList(HotelOwnerId);
    }
}
