
package com.microsercives.ratingservice.services;

import com.microsercives.ratingservice.dtos.CreateRatingRequestDTO;
import com.microsercives.ratingservice.dtos.RatingResponseDTO;

import java.util.List;

public interface RatingService {

    RatingResponseDTO createRating(CreateRatingRequestDTO requestDTO);

    List<RatingResponseDTO> getAllRatings();

    RatingResponseDTO getRatingById(String ratingId);

    List<RatingResponseDTO> getRatingsByUserId(String userId);

    List<RatingResponseDTO> getRatingsByHotelId(String hotelId);

    void deleteRating(String ratingId);
}