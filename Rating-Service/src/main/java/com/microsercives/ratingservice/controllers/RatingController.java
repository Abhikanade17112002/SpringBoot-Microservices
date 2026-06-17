package com.microsercives.ratingservice.controllers;

import com.microsercives.ratingservice.dtos.request.CreateRatingRequestDTO;
import com.microsercives.ratingservice.dtos.response.GetHotelAverageRatingResponseDTO;
import com.microsercives.ratingservice.dtos.response.RatingResponseDTO;
import com.microsercives.ratingservice.dtos.request.UpdateRatingRequestDTO;
import com.microsercives.ratingservice.services.RatingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private  RatingService ratingService;

    public RatingController() {
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    @Retry(
            name = "createNewRatingRetry",
            fallbackMethod = "createHotelFallBack"
    )
    @CircuitBreaker(name = "createNewRatingCB")
    public ResponseEntity<RatingResponseDTO> createRating(
            @Valid @RequestBody CreateRatingRequestDTO createRatingRequestDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ratingService.createRating(createRatingRequestDTO));
    }

    public ResponseEntity<RatingResponseDTO> createRatingFallback(
            @Valid @RequestBody CreateRatingRequestDTO createRatingRequestDTO , Exception e) {

        RatingResponseDTO response = new RatingResponseDTO();
        response.setRatingId("INVALID-RATING-ID");
        response.setRating(-1);
        response.setFeedback(e.getMessage());
        response.setCustomerId("INVALID-CUSTOMER-ID");
        response.setHotelId("INVALID-HOTEL-ID");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }


    @PreAuthorize(
            "hasAnyRole('ADMIN','OWNER','CUSTOMER')"
    )
    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingResponseDTO> getRatingById(
            @PathVariable(name = "ratingId") String ratingId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ratingService.getRatingById(ratingId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping
    public ResponseEntity<Page<RatingResponseDTO>> getAllRatings(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortby", defaultValue = "rating") String sortby,
            @RequestParam(name = "ascending", defaultValue = "true") Boolean ascending) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ratingService.getAllRatings(
                        page,
                        size,
                        sortby,
                        ascending));
    }
    @PreAuthorize(
            "hasAnyRole('ADMIN','CUSTOMER')"
    )
    @PutMapping("/{ratingId}")
    public ResponseEntity<RatingResponseDTO> updateRatingById(
            @PathVariable String ratingId,
            @Valid @RequestBody UpdateRatingRequestDTO updateRatingRequestDTO) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ratingService.updateRatingById(
                        ratingId,
                        updateRatingRequestDTO));
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN','CUSTOMER')"
    )
    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Boolean> deleteRatingById(
            @PathVariable String ratingId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ratingService.deleteRatingById(ratingId));
    }
    @PreAuthorize(
            "hasAnyRole('ADMIN','CUSTOMER')"
    )
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<Page<RatingResponseDTO>> getRatingsByCustomerId(
            @PathVariable String customerId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortby", defaultValue = "rating") String sortby,
            @RequestParam(name = "ascending", defaultValue = "true") Boolean ascending) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ratingService.getRatingsByCustomerId(
                        customerId,
                        page,
                        size,
                        sortby,
                        ascending));
    }

    @GetMapping("/hotels/{hotelId}")
    @PreAuthorize(
            "hasAnyRole('ADMIN','OWNER','CUSTOMER')"
    )
    public ResponseEntity<Page<RatingResponseDTO>> getRatingsByHotelId(
            @PathVariable String hotelId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortby", defaultValue = "rating") String sortby,
            @RequestParam(name = "ascending", defaultValue = "true") Boolean ascending) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ratingService.getRatingsByHotelId(
                        hotelId,
                        page,
                        size,
                        sortby,
                        ascending));
    }

    @GetMapping("/{hotelId}/average")
    @PreAuthorize(
            "hasAnyRole('ADMIN','OWNER','CUSTOMER')"
    )
    public ResponseEntity<GetHotelAverageRatingResponseDTO> getAverageRatingForHotel(
            @PathVariable String hotelId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ratingService.getAverageRatingForHotel(hotelId));
    }
}