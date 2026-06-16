package com.microsercives.userservice.services.impl;

import com.microsercives.userservice.dtos.UserResponseDTO;
import com.microsercives.userservice.dtos.request.DeleteUserAccountRequestDTO;
import com.microsercives.userservice.dtos.request.UpdateUserPasswordRequestDTO;
import com.microsercives.userservice.dtos.request.UpdateUserProfileRequestDTO;
import com.microsercives.userservice.entities.User;
import com.microsercives.userservice.enums.Role;
import com.microsercives.userservice.repositories.CustomerRepository;
import com.microsercives.userservice.repositories.HotelOwnerRepository;
import com.microsercives.userservice.repositories.UserRepository;
import com.microsercives.userservice.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder bCryptPasswordEncoder ;
    private static final Logger LOG = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);
    private  final  HotelOwnerRepository hotelOwnerRepository ;
    private final CustomerRepository customerRepository ;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper, PasswordEncoder bCryptPasswordEncoder, HotelOwnerRepository hotelOwnerRepository, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.hotelOwnerRepository = hotelOwnerRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findAllByEmailId(username);
    }

    @Override
    public UserResponseDTO getLoggedInUserProfile() {
        if(SecurityContextHolder.getContext().getAuthentication() == null ){
            throw new BadCredentialsException("Please Log In To Access User Profile");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        authenticatedUser = userRepository.findById(authenticatedUser.getUserId()).orElseThrow(()-> new EntityNotFoundException("User With Id Not Found")) ;
        return modelMapper.map(authenticatedUser, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updatedLoggedInUserProfile(UpdateUserProfileRequestDTO updateUserProfileRequestDTO) {
        if( !userRepository.existsById(updateUserProfileRequestDTO.getUserId()) ){
            throw new EntityNotFoundException("User With User Id ==> " + updateUserProfileRequestDTO.getUserId() + " Not Found") ;
        }

        User retrivedUser = userRepository.findById(updateUserProfileRequestDTO.getUserId()).orElseThrow(()->new EntityNotFoundException("User With Id Not Found"));
        retrivedUser.setLastName(updateUserProfileRequestDTO.getLastName() != null ? updateUserProfileRequestDTO.getLastName() : retrivedUser.getLastName() );
        retrivedUser.setFirstName(updateUserProfileRequestDTO.getFirstName() != null ? updateUserProfileRequestDTO.getFirstName() : retrivedUser.getFirstName());
        retrivedUser.setEmailId(updateUserProfileRequestDTO.getEmailId() != null ? updateUserProfileRequestDTO.getEmailId() : retrivedUser.getEmailId());
        retrivedUser.setDateOfBirth(updateUserProfileRequestDTO.getDateOfBirth() != null ? updateUserProfileRequestDTO.getDateOfBirth() : retrivedUser.getDateOfBirth());

        return modelMapper.map(userRepository.save(retrivedUser), UserResponseDTO.class);
    }

    @Override
    public Boolean updatedLoggedInUserPassword(UpdateUserPasswordRequestDTO updateUserPasswordRequestDTO) {
        if( !userRepository.existsById(updateUserPasswordRequestDTO.getUserId()) ){
            throw new EntityNotFoundException("User With User Id ==> " + updateUserPasswordRequestDTO.getUserId() + " Not Found") ;
        }
        User retrivedUser = userRepository.findById(updateUserPasswordRequestDTO.getUserId()).orElseThrow(()->new EntityNotFoundException("User With Id Not Found"));

        if(  bCryptPasswordEncoder.matches( updateUserPasswordRequestDTO.getOldPassword() , retrivedUser.getPassword()) ){

            retrivedUser.setPassword(bCryptPasswordEncoder.encode(  updateUserPasswordRequestDTO.getNewPassword() ));
            userRepository.save(retrivedUser);
            return true ;
        }
        else{
            LOG.info("Old Password  Do Not Match "  );
            return false ;
        }

    }

    @Override
    public Object deleteUserAccount(DeleteUserAccountRequestDTO deleteUserAccountRequestDTO) {

        if( !userRepository.existsById(deleteUserAccountRequestDTO.getUserId()) ){
            throw new EntityNotFoundException("User With User Id ==> " + deleteUserAccountRequestDTO.getUserId() + " Not Found") ;
        }
        User retrivedUser = userRepository.findById(deleteUserAccountRequestDTO.getUserId()).orElseThrow(()->new EntityNotFoundException("User With Id Not Found"));
        if( retrivedUser.getRole() == Role.ROLE_CUSTOMER ){
            if(  bCryptPasswordEncoder.matches( deleteUserAccountRequestDTO.getPassword() , retrivedUser.getPassword()) ){
                customerRepository.deleteById(retrivedUser.getUserId());
            }
            else{
                LOG.info("Old Password  Do Not Match " );
                return false ;
            }
        }
        else if(retrivedUser.getRole() == Role.ROLE_OWNER){
            if(  bCryptPasswordEncoder.matches( deleteUserAccountRequestDTO.getPassword() , retrivedUser.getPassword()) ){
                hotelOwnerRepository.deleteById(retrivedUser.getUserId());
            }
            else{
                LOG.info("Old Password  Do Not Match " );
                return false ;
            }
        }
        return true ;
    }

    @Override
    public List<UserResponseDTO> getAllRegisteredUsers() {
        List<User> reterivedUsers = userRepository.findAll();
        List<UserResponseDTO> response = reterivedUsers.stream().map(( user )-> modelMapper.map(user, UserResponseDTO.class)).toList();
        return response;
    }

    @Override
    public UserResponseDTO getRegisteredUserById(String userId) {
        if( !userRepository.existsById(userId) ){
            throw new EntityNotFoundException("User With User Id ==> " + userId + " Not Found") ;
        }
        User reterivedUser = userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException());
        return modelMapper.map(reterivedUser, UserResponseDTO.class);
    }

    @Override
    public Boolean deleteRegisteredUserById(String userId) {
        if( !userRepository.existsById(userId) ){
            throw new EntityNotFoundException("User With User Id ==> " + userId + " Not Found") ;
        }
        User retrivedUser = userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User With Id Not Found"));
        if( retrivedUser.getRole() == Role.ROLE_CUSTOMER ){
                customerRepository.deleteById(retrivedUser.getUserId());
        }
        else if(retrivedUser.getRole() == Role.ROLE_OWNER){
                hotelOwnerRepository.deleteById(retrivedUser.getUserId());
        }
        return true ;
    }
}