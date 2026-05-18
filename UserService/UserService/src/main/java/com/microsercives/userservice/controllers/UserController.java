package com.microsercives.userservice.controllers;
import com.microsercives.userservice.dtos.CreateUserRequestDTO;
import com.microsercives.userservice.entities.User;
import com.microsercives.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

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
    public ResponseEntity<User> getUserById(
            @PathVariable String userId) {

        User user = userService.getUserById(userId);

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