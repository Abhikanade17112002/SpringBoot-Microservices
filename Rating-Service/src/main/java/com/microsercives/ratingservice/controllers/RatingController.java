package com.microsercives.ratingservice.controllers;

import com.microsercives.ratingservice.dtos.request.CreateRatingRequestDTO;
import com.microsercives.ratingservice.dtos.response.GetHotelAverageRatingResponseDTO;
import com.microsercives.ratingservice.dtos.response.RatingResponseDTO;
import com.microsercives.ratingservice.dtos.request.UpdateRatingRequestDTO;
import com.microsercives.ratingservice.services.RatingService;
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

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<RatingResponseDTO> createRating(
            @Valid @RequestBody CreateRatingRequestDTO createRatingRequestDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ratingService.createRating(createRatingRequestDTO));
    }
    @PreAuthorize(
            "hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_CUSTOMER')"
    )
    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingResponseDTO> getRatingById(
            @PathVariable(name = "ratingId") String ratingId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ratingService.getRatingById(ratingId));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
            "hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER')"
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
            "hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER')"
    )
    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Boolean> deleteRatingById(
            @PathVariable String ratingId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ratingService.deleteRatingById(ratingId));
    }
    @PreAuthorize(
            "hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER')"
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
    public ResponseEntity<GetHotelAverageRatingResponseDTO> getAverageRatingForHotel(
            @PathVariable String hotelId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ratingService.getAverageRatingForHotel(hotelId));
    }
}