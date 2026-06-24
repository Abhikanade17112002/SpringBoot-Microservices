package com.microsercives.ratingservice.services;

public interface ValidationService {

    Boolean validateCustomer(String customerId);

    Boolean validateHotel(String hotelId);
}