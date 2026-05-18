package com.microsercives.userservice.services.impl;

import com.microsercives.userservice.dtos.CreateUserRequestDTO;
import com.microsercives.userservice.entities.User;
import com.microsercives.userservice.repositories.UserRepository;
import com.microsercives.userservice.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // CREATE USER
    @Override
    public User addNewUser(CreateUserRequestDTO createUserRequestDTO) {

        User user = modelMapper.map(createUserRequestDTO, User.class);

        return userRepository.save(user);
    }
    // GET USER BY ID
    @Override
    public User getUserById(String userId) {

        return userRepository.findById(String.valueOf(userId))
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));
    }

    // GET ALL USERS
    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    // UPDATE USER
    @Override
    public User updateUser(String userId,
                           CreateUserRequestDTO updateUserRequestDTO) {

        User existingUser = userRepository.findById(String.valueOf(userId))
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        existingUser.setFirstName(updateUserRequestDTO.getFirstName());
        existingUser.setLastName(updateUserRequestDTO.getLastName());
        existingUser.setEmailId(updateUserRequestDTO.getEmailId());
        existingUser.setDateOfBirth(updateUserRequestDTO.getDateOfBirth());

        return userRepository.save(existingUser);
    }

    // DELETE USER
    @Override
    public void deleteUser(String userId) {

        User existingUser = userRepository.findById(String.valueOf(userId))
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        userRepository.delete(existingUser);
    }
}