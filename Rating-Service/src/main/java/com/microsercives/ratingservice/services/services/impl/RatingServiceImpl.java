// RatingServiceImpl

package com.microsercives.ratingservice.services.services.impl;

import com.microsercives.ratingservice.dtos.CreateRatingRequestDTO;
import com.microsercives.ratingservice.dtos.RatingResponseDTO;
import com.microsercives.ratingservice.entities.Rating;
import com.microsercives.ratingservice.repositories.RatingRepository;
import com.microsercives.ratingservice.services.RatingService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final ModelMapper modelMapper;
    private final RatingRepository ratingRepository;

    public RatingServiceImpl(ModelMapper modelMapper,
                             RatingRepository ratingRepository) {
        this.modelMapper = modelMapper;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public RatingResponseDTO createRating(CreateRatingRequestDTO requestDTO) {

        Rating rating = modelMapper.map(requestDTO, Rating.class);

        Rating savedRating = ratingRepository.save(rating);

        return modelMapper.map(savedRating, RatingResponseDTO.class);
    }

    @Override
    public List<RatingResponseDTO> getAllRatings() {

        List<Rating> ratings = ratingRepository.findAll();

        return ratings.stream()
                .map(rating ->
                        modelMapper.map(rating, RatingResponseDTO.class))
                .toList();
    }

    @Override
    public RatingResponseDTO getRatingById(String ratingId) {

        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Rating not found with id : " + ratingId
                        ));

        return modelMapper.map(rating, RatingResponseDTO.class);
    }

    @Override
    public List<RatingResponseDTO> getRatingsByUserId(String userId) {

        List<Rating> ratings = ratingRepository.findByUserId(userId);

        return ratings.stream()
                .map(rating ->
                        modelMapper.map(rating, RatingResponseDTO.class))
                .toList();
    }

    @Override
    public List<RatingResponseDTO> getRatingsByHotelId(String hotelId) {

        List<Rating> ratings = ratingRepository.findByHotelId(hotelId);

        return ratings.stream()
                .map(rating ->
                        modelMapper.map(rating, RatingResponseDTO.class))
                .toList();
    }

    @Override
    public void deleteRating(String ratingId) {

        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Rating not found with id : " + ratingId
                        ));

        ratingRepository.delete(rating);
    }
}