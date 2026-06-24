package com.microsercives.hotelservice.services.impl;

import com.microsercives.hotelservice.dtos.request.CreateHotelRequestDTO;
import com.microsercives.hotelservice.dtos.request.UpdateHotelRequestDTO;
import com.microsercives.hotelservice.dtos.response.HotelResponseDTO;
import com.microsercives.hotelservice.dtos.response.HotelValidationResponseDTO;
import com.microsercives.hotelservice.dtos.response.ListOfHotelOwnerHotelIdsListResponseDTO;
import com.microsercives.hotelservice.entities.Hotel;
import com.microsercives.hotelservice.repositories.HotelRepositories;
import com.microsercives.hotelservice.services.HotelOwnerVerificationService;
import com.microsercives.hotelservice.services.HotelService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private HotelRepositories repositories;
    @Autowired
    private HotelOwnerVerificationService verificationService;
    private Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);

    private final HotelRepositories hotelRepositories;

    public HotelServiceImpl(HotelRepositories hotelRepositories) {
        this.hotelRepositories = hotelRepositories;
    }

    // CREATE
    @Override
    public HotelResponseDTO createHotel(CreateHotelRequestDTO createHotelRequestDTO) {

        if( !verificationService.verifyHotelOwner(createHotelRequestDTO.getOwnerId()) ){
            logger.info("Hotel owner verification failed");
            return new HotelResponseDTO() ;
        }

        Hotel hotel = new Hotel();
        hotel.setHotelName(createHotelRequestDTO.getHotelName());
        hotel.setActive(true);
        hotel.setHotelName(createHotelRequestDTO.getHotelName());
        hotel.setOwnerId(createHotelRequestDTO.getOwnerId());
        hotel.setDescription(createHotelRequestDTO.getDescription());
        hotel.setLocation(createHotelRequestDTO.getLocation());
        Hotel savedHotel =  hotelRepositories.save(hotel);
        return modelMapper.map(savedHotel, HotelResponseDTO.class);
    }


    @Override
    public Page<HotelResponseDTO> getAllHotels(int page, int size, String sortby, Boolean ascending) {
        Sort sort =  ascending ?  Sort.by(sortby).ascending() : Sort.by(sortby).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Hotel> reterivedHotels = hotelRepositories.findAll(pageable) ;
        return reterivedHotels.map((hotel)->modelMapper.map(hotel, HotelResponseDTO.class)) ;
    }

    @Override
    public HotelResponseDTO getHotelById(String hotelId) {
        if( !hotelRepositories.existsById(hotelId) ){
            logger.info("Hotel By Id {} Not Found", hotelId);
            throw new EntityNotFoundException("Hotel By Id " + hotelId + " Not Found");
        }
        Hotel reterivedHotel = hotelRepositories.findById(hotelId).orElse(new Hotel());
        return modelMapper.map(reterivedHotel, HotelResponseDTO.class);
    }

    @Override
    public HotelResponseDTO updateHotelById(String hotelId, UpdateHotelRequestDTO updateHotelRequestDTO) {
        if( !hotelRepositories.existsById(hotelId) ){
            logger.info("Hotel By Id {} Not Found", hotelId);
            throw new EntityNotFoundException("Hotel By Id " + hotelId + " Not Found");
        }
        Hotel reterivedHotel = hotelRepositories.findById(hotelId).orElse(new Hotel());
        reterivedHotel.setHotelName(updateHotelRequestDTO.getHotelName() !=  null ? updateHotelRequestDTO.getHotelName() : reterivedHotel.getHotelName());
        reterivedHotel.setLocation(updateHotelRequestDTO.getLocation() !=  null ? updateHotelRequestDTO.getLocation() : reterivedHotel.getLocation());
        reterivedHotel.setDescription(updateHotelRequestDTO.getDescription()  !=  null ? updateHotelRequestDTO.getDescription() : reterivedHotel.getDescription());
        Hotel updatedHotel = hotelRepositories.save(reterivedHotel);
        return modelMapper.map(updatedHotel, HotelResponseDTO.class);
    }

    @Override
    public void deleteHotelById(String hotelId) {
        if( !hotelRepositories.existsById(hotelId) ){
            logger.info("Hotel By Id {} Not Found", hotelId);
            throw new EntityNotFoundException("Hotel By Id " + hotelId + " Not Found");
        }
        hotelRepositories.deleteById(hotelId);
        return ;
    }

    @Override
    public Page<HotelResponseDTO> findByHotelNameContainingIgnoreCase(String hotelName, int page, int size, String sortby, Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortby).ascending():Sort.by(sortby).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Hotel> reterivedHotels = hotelRepositories.findByHotelNameContainingIgnoreCase(hotelName,pageable) ;
        return reterivedHotels.map((hotel)->modelMapper.map(hotel, HotelResponseDTO.class)) ;
    }

    @Override
    public Page<HotelResponseDTO> findByLocationContainingIgnoreCase(String location, int page, int size, String sortby, Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortby).ascending():Sort.by(sortby).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Hotel> reterivedHotels = hotelRepositories.findByLocationContainingIgnoreCase(location,pageable) ;
        return reterivedHotels.map((hotel)->modelMapper.map(hotel, HotelResponseDTO.class)) ;
    }

    @Override
    public Page<HotelResponseDTO> findHotelsByOwnerId(String ownerId, int page, int size, String sortby, Boolean ascending) {
        Sort sort = ascending ? Sort.by(sortby).ascending():Sort.by(sortby).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Hotel> reterivedHotels = hotelRepositories.findByOwnerId(ownerId,pageable) ;
        return reterivedHotels.map((hotel)->modelMapper.map(hotel, HotelResponseDTO.class)) ;
    }

    @Override
    public HotelValidationResponseDTO validateHotel(String hotelId) {
        if( !hotelRepositories.existsById(hotelId) ){
            logger.info("Hotel By Id {} Not Found", hotelId);
            return new HotelValidationResponseDTO(hotelId,false);
        }
        Hotel hotel = hotelRepositories.findById(hotelId).orElse(new Hotel());
        if (hotel.isActive()){
            return new HotelValidationResponseDTO(hotelId,true);
        }
        return new HotelValidationResponseDTO(hotelId,false);
    }

    @Override
    public ListOfHotelOwnerHotelIdsListResponseDTO getOwnerHotelsIdList(String ownerId) {
        if( !verificationService.verifyHotelOwner(ownerId) ){
            logger.info("Hotel owner verification failed");
            return new ListOfHotelOwnerHotelIdsListResponseDTO();
        }
        Sort sort = Sort.by("hotelId").ascending() ;
        Pageable pageable = PageRequest.of(0, 10000, sort);
        Page<Hotel> retreivedHotels = hotelRepositories.findByOwnerId(ownerId,pageable);
        List<String> hotelIds = retreivedHotels.map((hotel)->hotel.getHotelId()).stream().toList();

        logger.info("Hotel owner ids {}", hotelIds);

        ListOfHotelOwnerHotelIdsListResponseDTO response =  new ListOfHotelOwnerHotelIdsListResponseDTO(hotelIds);

        return response;
    }

}