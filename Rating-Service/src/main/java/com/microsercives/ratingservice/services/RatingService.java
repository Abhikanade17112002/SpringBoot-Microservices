
package com.microsercives.ratingservice.services;

import com.microsercives.ratingservice.dtos.CreateRatingRequestDTO;
import com.microsercives.ratingservice.dtos.RatingResponseDTO;
import com.microsercives.ratingservice.dtos.UpdateRatingRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RatingService {
    RatingResponseDTO createRating(@Valid CreateRatingRequestDTO createRatingRequestDTO);

    RatingResponseDTO getRatingById(String ratingId);

    Page<RatingResponseDTO> getAllRatings(int page, int size, String sortby, Boolean ascending);

    RatingResponseDTO updateRatingById(String ratingId, @Valid UpdateRatingRequestDTO updateRatingRequestDTO);

    Boolean deleteRatingById(String ratingId);

    Page<RatingResponseDTO> getRatingsByCustomerId(String customerId, int page, int size, String sortby, Boolean ascending);

    Page<RatingResponseDTO> getRatingsByHotelId(String hotelId, int page, int size, String sortby, Boolean ascending);

    Double getAverageRatingForHotel(String hotelId);
}