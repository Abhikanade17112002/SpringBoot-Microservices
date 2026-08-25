package com.microsercives.userservice.services;
import com.microsercives.userservice.dtos.response.UserResponseDTO;
import com.microsercives.userservice.dtos.request.DeleteUserAccountRequestDTO;
import com.microsercives.userservice.dtos.request.UpdateUserPasswordRequestDTO;
import com.microsercives.userservice.dtos.request.UpdateUserProfileRequestDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserService extends UserDetailsService {


    UserResponseDTO getLoggedInUserProfile();

    UserResponseDTO updatedLoggedInUserProfile(UpdateUserProfileRequestDTO updateUserProfileRequestDTO);

    Boolean updatedLoggedInUserPassword(UpdateUserPasswordRequestDTO updateUserPasswordRequestDTO);

    Object deleteUserAccount(DeleteUserAccountRequestDTO deleteUserAccountRequestDTO);

    List<UserResponseDTO> getAllRegisteredUsers();

    UserResponseDTO getRegisteredUserById(String userId);

    Boolean deleteRegisteredUserById(String userId);

    public UserResponseDTO uploadProfileImage(MultipartFile file) ;

    public String getProfileImageUrl();

    void deleteProfileImage();
}