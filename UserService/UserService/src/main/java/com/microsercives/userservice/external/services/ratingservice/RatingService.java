package com.microsercives.userservice.external.services.ratingservice;

import com.microsercives.userservice.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RATING-SERVICE" , path = "/api/v1")
public interface RatingService {
    @GetMapping("/ratings/users/{userId}")
    List<Rating> getUserRatings(@PathVariable(name = "userId") String userId);
}
