package com.microsercives.hotelservice.services.impl;

import com.microsercives.hotelservice.dtos.response.HotelResponseDTO;
import com.microsercives.hotelservice.dtos.response.OwnerValidationResponseDTO;
import com.microsercives.hotelservice.external.service.userservice.UserService;
import com.microsercives.hotelservice.services.HotelOwnerVerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelOwnerVerificationServiceImpl implements HotelOwnerVerificationService {
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(HotelOwnerVerificationServiceImpl.class);
    public Boolean verifyHotelOwner(String hotelOwnerId) {
        logger.info("HotelOwnerVerificationServiceImpl.verifyHotelOwner()");
        OwnerValidationResponseDTO responseDTO = userService.validateOwner(hotelOwnerId);
        return responseDTO.getActive();
    }
}
