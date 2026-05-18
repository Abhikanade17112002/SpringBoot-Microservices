package com.microsercives.userservice.services;
import com.microsercives.userservice.dtos.CreateUserRequestDTO;
import com.microsercives.userservice.entities.User;

import java.util.List;


public interface UserService {

    // CREATE
    User addNewUser(CreateUserRequestDTO createUserRequestDTO);

    // READ
    User getUserById(String userId);

    List<User> getAllUsers();

    // UPDATE
    User updateUser(String userId, CreateUserRequestDTO updateUserRequestDTO);

    // DELETE
    void deleteUser(String userId);
}