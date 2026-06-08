package com.microsercives.ratingservice.repositories;

import com.microsercives.ratingservice.entities.Rating;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {
    Optional<Rating> findByCustomerIdAndHotelId(
            String customerId,
            String hotelId
    );

    Page<Rating> findByCustomerId(
            String customerId,
            Pageable pageable
    );

    Page<Rating> findByHotelId(
            String hotelId,
            Pageable pageable
    );

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.hotelId = :hotelId")
    Double findAverageRatingByHotelId(@Param("hotelId") String hotelId);

    long countByHotelId(String hotelId);
}