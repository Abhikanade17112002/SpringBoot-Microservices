package com.microsercives.userservice.controllers;
import com.microsercives.userservice.dtos.CreateUserRequestDTO;
import com.microsercives.userservice.entities.Hotel;
import com.microsercives.userservice.entities.Rating;
import com.microsercives.userservice.entities.User;
import com.microsercives.userservice.external.services.hotelservice.HotelService;
import com.microsercives.userservice.external.services.ratingservice.RatingService;
import com.microsercives.userservice.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private static final String HOTEL_SERVICE_URL = "http://HOTEL-SERVICE/api/v1/hotels/" ;
    private static final String RATING_SERVICE_URL = "http://RATING-SERVICE/api/v1/ratings/users/";
    private final UserService userService;
    @Autowired
    private RatingService ratingService ;
    @Autowired
    private HotelService hotelService ;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE USER
    @PostMapping
    public ResponseEntity<User> addNewUser(
            @RequestBody CreateUserRequestDTO createUserRequestDTO) {

        User savedUser = userService.addNewUser(createUserRequestDTO);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // GET USER BY ID
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "getSingleUserBreaker" , fallbackMethod = "getUserByIdFallback")
    public ResponseEntity<User> getUserById(
            @PathVariable String userId) {

        User user = userService.getUserById(userId);
        List<Rating> userRatingsList = ratingService.getUserRatings(user.getId());

        userRatingsList = userRatingsList.stream().map((rating)->{
            ResponseEntity<Hotel> retreivedHotel = hotelService.getHotelById(rating.getHotelId());
            rating.setHotel(retreivedHotel.getBody());
           return rating ;
        }).toList();

        user.setRatings(userRatingsList);

        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> getUserByIdFallback(String userId , Exception e){
        System.out.println("GetUserByIdFallback Fallback Methode Executed ==> " + e.getMessage());
        List<Rating> userRatingsList = List.of();
        return ResponseEntity.ok(new User("Id","First Name","Last Name","first.last@email.com", LocalDate.now(),userRatingsList));

    }

    // GET ALL USERS
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    // UPDATE USER
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable String userId,
            @RequestBody CreateUserRequestDTO updateUserRequestDTO) {

        User updatedUser = userService.updateUser(userId,
                updateUserRequestDTO);

        return ResponseEntity.ok(updatedUser);
    }

    // DELETE USER
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable String userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok("User Deleted Successfully");
    }
}