// RatingController

package com.microsercives.ratingservice.controllers;

import com.microsercives.ratingservice.dtos.CreateRatingRequestDTO;
import com.microsercives.ratingservice.dtos.RatingResponseDTO;
import com.microsercives.ratingservice.services.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // Create Rating
    @PostMapping
    public ResponseEntity<RatingResponseDTO> createRating(
            @RequestBody CreateRatingRequestDTO requestDTO
    ) {

        RatingResponseDTO response =
                ratingService.createRating(requestDTO);

        return ResponseEntity.ok(response);
    }

    // Get All Ratings
    @GetMapping
    public ResponseEntity<List<RatingResponseDTO>> getAllRatings() {

        return ResponseEntity.ok(
                ratingService.getAllRatings()
        );
    }

    // Get Rating By Id
    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingResponseDTO> getRatingById(
            @PathVariable String ratingId
    ) {

        return ResponseEntity.ok(
                ratingService.getRatingById(ratingId)
        );
    }

    // Get Ratings By User Id
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<RatingResponseDTO>> getRatingsByUserId(
            @PathVariable String userId
    ) {

        return ResponseEntity.ok(
                ratingService.getRatingsByUserId(userId)
        );
    }

    // Get Ratings By Hotel Id
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<RatingResponseDTO>> getRatingsByHotelId(
            @PathVariable String hotelId
    ) {

        return ResponseEntity.ok(
                ratingService.getRatingsByHotelId(hotelId)
        );
    }

    // Delete Rating
    @DeleteMapping("/{ratingId}")
    public ResponseEntity<String> deleteRating(
            @PathVariable String ratingId
    ) {

        ratingService.deleteRating(ratingId);

        return ResponseEntity.ok(
                "Rating deleted successfully !!"
        );
    }
}