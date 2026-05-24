package com.microsercives.userservice.controllers;
import com.microsercives.userservice.dtos.CreateUserRequestDTO;
import com.microsercives.userservice.entities.Rating;
import com.microsercives.userservice.entities.User;
import com.microsercives.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private static final String RATING_SERVICE_URL = "http://localhost:8082/api/v1/ratings/users/";
    private final UserService userService;
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
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
    public ResponseEntity<User> getUserById(
            @PathVariable String userId) {

        User user = userService.getUserById(userId);


        List<Rating> userRatings = restTemplate.getForObject(RATING_SERVICE_URL + userId, List.class);

        user.setRatings(userRatings);

        return ResponseEntity.ok(user);
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