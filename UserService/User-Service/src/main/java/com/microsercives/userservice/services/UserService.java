package com.microsercives.userservice.services;
import com.microsercives.userservice.dtos.CreateUserRequestDTO;
import com.microsercives.userservice.dtos.UserResponseDTO;
import com.microsercives.userservice.dtos.request.DeleteUserAccountRequestDTO;
import com.microsercives.userservice.dtos.request.UpdateUserPasswordRequestDTO;
import com.microsercives.userservice.dtos.request.UpdateUserProfileRequestDTO;
import com.microsercives.userservice.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {


    UserResponseDTO getLoggedInUserProfile();

    UserResponseDTO updatedLoggedInUserProfile(UpdateUserProfileRequestDTO updateUserProfileRequestDTO);

    Boolean updatedLoggedInUserPassword(UpdateUserPasswordRequestDTO updateUserPasswordRequestDTO);

    Object deleteUserAccount(DeleteUserAccountRequestDTO deleteUserAccountRequestDTO);

    List<UserResponseDTO> getAllRegisteredUsers();

    UserResponseDTO getRegisteredUserById(String userId);

    Boolean deleteRegisteredUserById(String userId);
}