package com.microsercives.userservice.services.impl;

import com.microsercives.userservice.dtos.request.CustomerSignUpRequestDTO;
import com.microsercives.userservice.dtos.request.HotelOwnerSignUpRequestDTO;
import com.microsercives.userservice.dtos.request.UserSignInRequestDTO;
import com.microsercives.userservice.dtos.response.CustomerSignUpResponseDTO;
import com.microsercives.userservice.dtos.response.HotelOwnerSignUpResponseDTO;
import com.microsercives.userservice.dtos.response.UserSignInResponseDTO;
import com.microsercives.userservice.entities.Customer;
import com.microsercives.userservice.entities.HotelOwner;
import com.microsercives.userservice.entities.User;
import com.microsercives.userservice.enums.Role;
import com.microsercives.userservice.repositories.CustomerRepository;
import com.microsercives.userservice.repositories.HotelOwnerRepository;
import com.microsercives.userservice.repositories.UserRepository;
import com.microsercives.userservice.services.AuthenticationService;
import com.microsercives.userservice.utility.JWTUtility;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private CustomerRepository customerRepository ;
    @Autowired
    private HotelOwnerRepository hotelOwnerRepository ;
    @Autowired
    private ModelMapper modelMapper ;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder ;
    @Autowired
    private AuthenticationManager authenticationManager ;
    @Autowired
    private JWTUtility jwtUtility ;


    @Override
    @Transactional
    public CustomerSignUpResponseDTO registerCustomer(CustomerSignUpRequestDTO customerSignUpRequestDTO) {

        if(userRepository.existsByEmailId( customerSignUpRequestDTO.getEmailId() )){
            throw new EntityExistsException("Customer With Email Id " + customerSignUpRequestDTO.getEmailId() + " Already Exists ! ");
        }

        User savedUser = getSavedUserFromRequestDTO(customerSignUpRequestDTO,null);
        Customer newCustomer = new Customer() ;

        newCustomer.setActive(true);
        newCustomer.setUser(savedUser);

        Customer savedCustomer = customerRepository.save(newCustomer);

        return modelMapper.map(savedCustomer,CustomerSignUpResponseDTO.class);
    }

    @Override
    @Transactional
    public HotelOwnerSignUpResponseDTO registerHotelOwner(HotelOwnerSignUpRequestDTO hotelOwnerSignUpRequestDTO) {
        if(userRepository.existsByEmailId( hotelOwnerSignUpRequestDTO.getEmailId() )){
            throw new EntityExistsException("Hotel Owner With Email Id " + hotelOwnerSignUpRequestDTO.getEmailId() + " Already Exists ! ");
        }

        User savedUser = getSavedUserFromRequestDTO(null,hotelOwnerSignUpRequestDTO);
        HotelOwner hotelOwner = new HotelOwner() ;

        hotelOwner.setActive(true);
        hotelOwner.setUser(savedUser);

        HotelOwner savedHotelOwner = hotelOwnerRepository.save(hotelOwner) ;

        return modelMapper.map(savedHotelOwner,HotelOwnerSignUpResponseDTO.class);
    }

    @Override
    public UserSignInResponseDTO signInUser(UserSignInRequestDTO userSignInRequestDTO) {

        if( ! userRepository.existsByEmailId(userSignInRequestDTO.getEmailId()) ){
            throw new EntityNotFoundException("User With Email Id " + userSignInRequestDTO.getEmailId() + " Does Not Exists");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userSignInRequestDTO.getEmailId(),userSignInRequestDTO.getPassword()) ;
        Authentication authentication = authenticationManager.authenticate(token);
        User authenticatedUser = (User)authentication.getPrincipal() ;
        String jwtToken = "Bearer " + jwtUtility.generateToken(authenticatedUser);
        UserSignInResponseDTO responseDTO = new UserSignInResponseDTO(jwtToken,"Bearer",authenticatedUser.getRole().name(), authenticatedUser.getUserId()) ;
        return responseDTO;
    }

    @Override
    public User getSavedUserFromRequestDTO(CustomerSignUpRequestDTO customerSignUpRequestDTO, HotelOwnerSignUpRequestDTO hotelOwnerSignUpRequestDTO) {

        User savedUser = null ;

        if( customerSignUpRequestDTO != null ){

            String userName = customerSignUpRequestDTO.getFirstName() + customerSignUpRequestDTO.getLastName() + "@" + (int)(Math.random()*99999 + 1) ;
            User newUser = new User();
            newUser.setRole(Role.ROLE_CUSTOMER);
            newUser.setUserName(userName);
            newUser.setDateOfBirth(customerSignUpRequestDTO.getDateOfBirth());
            newUser.setEmailId(customerSignUpRequestDTO.getEmailId());
            newUser.setPassword(bCryptPasswordEncoder.encode( customerSignUpRequestDTO.getPassword() ));
            newUser.setFirstName(customerSignUpRequestDTO.getFirstName());
            newUser.setLastName(customerSignUpRequestDTO.getLastName());

            savedUser = userRepository.save(newUser);

        } else {
            String userName = hotelOwnerSignUpRequestDTO.getFirstName() + hotelOwnerSignUpRequestDTO.getLastName() + "@" + (int)(Math.random()*99999 + 1) ;
            User newUser = new User();
            newUser.setRole(Role.ROLE_OWNER);
            newUser.setUserName(userName);
            newUser.setDateOfBirth(hotelOwnerSignUpRequestDTO.getDateOfBirth());
            newUser.setEmailId(hotelOwnerSignUpRequestDTO.getEmailId());
            newUser.setPassword(bCryptPasswordEncoder.encode( hotelOwnerSignUpRequestDTO.getPassword() ));
            newUser.setFirstName(hotelOwnerSignUpRequestDTO.getFirstName());
            newUser.setLastName(hotelOwnerSignUpRequestDTO.getLastName());

            savedUser = userRepository.save(newUser);
        }

        return savedUser ;
    }
}
