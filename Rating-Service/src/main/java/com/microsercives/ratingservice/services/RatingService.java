
package com.microsercives.ratingservice.services;

import com.microsercives.ratingservice.dtos.request.CreateRatingRequestDTO;
import com.microsercives.ratingservice.dtos.response.GetHotelAverageRatingResponseDTO;
import com.microsercives.ratingservice.dtos.response.RatingResponseDTO;
import com.microsercives.ratingservice.dtos.request.UpdateRatingRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface RatingService {
    RatingResponseDTO createRating(@Valid CreateRatingRequestDTO createRatingRequestDTO);

    RatingResponseDTO getRatingById(String ratingId);

    Page<RatingResponseDTO> getAllRatings(int page, int size, String sortby, Boolean ascending);

    RatingResponseDTO updateRatingById(String ratingId, @Valid UpdateRatingRequestDTO updateRatingRequestDTO);

    Boolean deleteRatingById(String ratingId);

    Page<RatingResponseDTO> getRatingsByCustomerId(String customerId, int page, int size, String sortby, Boolean ascending);

    Page<RatingResponseDTO> getRatingsByHotelId(String hotelId, int page, int size, String sortby, Boolean ascending);

    GetHotelAverageRatingResponseDTO getAverageRatingForHotel(String hotelId);
}