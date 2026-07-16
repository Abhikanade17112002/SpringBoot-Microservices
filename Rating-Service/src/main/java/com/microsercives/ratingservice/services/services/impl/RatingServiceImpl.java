package com.microsercives.ratingservice.services.services.impl;

import com.microsercives.ratingservice.dtos.request.CreateRatingRequestDTO;
import com.microsercives.ratingservice.dtos.response.GetHotelAverageRatingResponseDTO;
import com.microsercives.ratingservice.dtos.response.RatingResponseDTO;
import com.microsercives.ratingservice.dtos.request.UpdateRatingRequestDTO;
import com.microsercives.ratingservice.entities.AuthenticatedUser;
import com.microsercives.ratingservice.entities.Rating;
import com.microsercives.ratingservice.repositories.RatingRepository;
import com.microsercives.ratingservice.services.RatingService;
import com.microsercives.ratingservice.services.ValidationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    private Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);
    private final  RatingRepository ratingRepository;
    private final ModelMapper modelMapper;
    private final ValidationService validationService;

    public RatingServiceImpl(RatingRepository ratingRepository, ModelMapper modelMapper, ValidationService validationService) {
        this.ratingRepository = ratingRepository;
        this.modelMapper = modelMapper;
        this.validationService = validationService;
    }

    @Override
    public RatingResponseDTO createRating(CreateRatingRequestDTO createRatingRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        if( !validationService.validateCustomer(authenticatedUser.getUserId())){
            logger.info("Customer With Provided Id Not Active");
            throw new RuntimeException("Customer With Provided Id Id Not Active");
        }
        if( !validationService.validateHotel(createRatingRequestDTO.getHotelId())){
            logger.info("Hotel With Provided Id  Not Active");
            throw new RuntimeException("Hotel With Provided  Id Not Active");
        }
        String authUserId = authenticatedUser.getUserId();
        String hotelId = createRatingRequestDTO.getHotelId();
        Optional<Rating> rerating = ratingRepository.findByCustomerIdAndHotelId(authenticatedUser.getUserId(),createRatingRequestDTO.getHotelId());
        System.out.println("authUserId ==> " +  authUserId + " hotelId ==> " + hotelId + " rating ==> " + rerating);

        if( rerating.isPresent() ){
            logger.info("Rating With Hotel Id And Customer Id Already Exists");
            return modelMapper.map( rerating, RatingResponseDTO.class);
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
    public GetHotelAverageRatingResponseDTO getAverageRatingForHotel(
            String hotelId) {
        Double averageRating = ratingRepository.findAverageRatingByHotelId(hotelId);
        long totalNoOfRatings = ratingRepository.countByHotelId(hotelId);
        System.out.println("totalNoOfRatings = " + totalNoOfRatings);
        System.out.println("averageRating = " + averageRating);
        GetHotelAverageRatingResponseDTO responseDTO = new GetHotelAverageRatingResponseDTO();
        responseDTO.setAverageRating(averageRating == null ? 0.0 : averageRating);
        responseDTO.setHotelId(hotelId);
        responseDTO.setNoOfRatings((int)totalNoOfRatings);
        return responseDTO;
    }
}