package com.microsercives.ratingservice.services.services.impl;

import com.microsercives.ratingservice.dtos.CreateRatingRequestDTO;
import com.microsercives.ratingservice.dtos.RatingResponseDTO;
import com.microsercives.ratingservice.dtos.UpdateRatingRequestDTO;
import com.microsercives.ratingservice.entities.AuthenticatedUser;
import com.microsercives.ratingservice.entities.Rating;
import com.microsercives.ratingservice.repositories.RatingRepository;
import com.microsercives.ratingservice.services.RatingService;
import com.microsercives.ratingservice.utility.ValidationUtility;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    private Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ValidationUtility validationUtility;

    public RatingServiceImpl() {
    }

    @Override
    public RatingResponseDTO createRating(
            CreateRatingRequestDTO createRatingRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        if( !validationUtility.validateCustomer(authenticatedUser.getUserId())){
            logger.info("Customer With Provided Id Not Active");
            throw new RuntimeException("Customer With Provided Id Id Not Active");
        }
        if( !validationUtility.validateHotel(createRatingRequestDTO.getHotelId())){
            logger.info("Hotel With Provided Id  Not Active");
            throw new RuntimeException("Hotel With Provided  Id Not Active");
        }
        if(ratingRepository.findByCustomerIdAndHotelId(authenticatedUser.getUserId(),createRatingRequestDTO.getHotelId()) != null ){
            logger.info("Rating With Hotel Id And Customer Id Already Exists");
            return modelMapper.map( ratingRepository.findByCustomerIdAndHotelId(authenticatedUser.getUserId(),createRatingRequestDTO.getHotelId()),RatingResponseDTO.class);
        }
        Rating rating = modelMapper.map(createRatingRequestDTO, Rating.class);
        rating.setCustomerId(authenticatedUser.getUserId());
        Rating savedRating =
                ratingRepository.save(rating);
        return modelMapper.map(
                savedRating,
                RatingResponseDTO.class
        );
    }

    @Override
    public RatingResponseDTO getRatingById(
            String ratingId) {

        Rating retrievedRating =
                ratingRepository.findById(ratingId)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Rating With Id ==> "
                                                + ratingId
                                                + " Not Found"));

        return modelMapper.map(
                retrievedRating,
                RatingResponseDTO.class
        );
    }

    @Override
    public Page<RatingResponseDTO> getAllRatings(
            int page,
            int size,
            String sortby,
            Boolean ascending) {

        Sort sort = ascending
                ? Sort.by(sortby).ascending()
                : Sort.by(sortby).descending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<Rating> retrievedRatings =
                ratingRepository.findAll(pageable);

        return retrievedRatings.map(
                rating -> modelMapper.map(
                        rating,
                        RatingResponseDTO.class
                )
        );
    }

    @Override
    public RatingResponseDTO updateRatingById(
            String ratingId,
            UpdateRatingRequestDTO updateRatingRequestDTO) {

        Rating retrievedRating =
                ratingRepository.findById(ratingId)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Rating With Id ==> "
                                                + ratingId
                                                + " Not Found"));

        retrievedRating.setRating(
                updateRatingRequestDTO.getRating()
        );

        retrievedRating.setFeedback(
                updateRatingRequestDTO.getFeedback()
        );

        Rating updatedRating =
                ratingRepository.save(
                        retrievedRating
                );

        return modelMapper.map(
                updatedRating,
                RatingResponseDTO.class
        );
    }

    @Override
    public Boolean deleteRatingById(
            String ratingId) {

        Rating retrievedRating =
                ratingRepository.findById(ratingId)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Rating With Id ==> "
                                                + ratingId
                                                + " Not Found"));

        ratingRepository.delete(retrievedRating);

        return true;
    }

    @Override
    public Page<RatingResponseDTO> getRatingsByCustomerId(
            String customerId,
            int page,
            int size,
            String sortby,
            Boolean ascending) {

        Sort sort = ascending
                ? Sort.by(sortby).ascending()
                : Sort.by(sortby).descending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<Rating> retrievedRatings =
                ratingRepository.findByCustomerId(
                        customerId,
                        pageable
                );

        return retrievedRatings.map(
                rating -> modelMapper.map(
                        rating,
                        RatingResponseDTO.class
                )
        );
    }

    @Override
    public Page<RatingResponseDTO> getRatingsByHotelId(
            String hotelId,
            int page,
            int size,
            String sortby,
            Boolean ascending) {

        Sort sort = ascending
                ? Sort.by(sortby).ascending()
                : Sort.by(sortby).descending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<Rating> retrievedRatings =
                ratingRepository.findByHotelId(
                        hotelId,
                        pageable
                );

        return retrievedRatings.map(
                rating -> modelMapper.map(
                        rating,
                        RatingResponseDTO.class
                )
        );
    }

    @Override
    public Double getAverageRatingForHotel(
            String hotelId) {

        Double averageRating =
                ratingRepository
                        .findAverageRatingByHotelId(
                                hotelId
                        );

        return averageRating == null
                ? 0.0
                : averageRating;
    }
}