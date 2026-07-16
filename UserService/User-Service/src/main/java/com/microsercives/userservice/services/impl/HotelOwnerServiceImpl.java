package com.microsercives.userservice.services.impl;

import com.microsercives.userservice.dtos.response.OwnerResponseDTO;
import com.microsercives.userservice.dtos.response.OwnerValidationResponseDTO;
import com.microsercives.userservice.entities.HotelOwner;
import com.microsercives.userservice.repositories.HotelOwnerRepository;
import com.microsercives.userservice.services.HotelOwnerService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class HotelOwnerServiceImpl implements HotelOwnerService {
    private Logger logger = LoggerFactory.getLogger(HotelOwnerServiceImpl.class);
    @Autowired
    private ModelMapper modelMapper ;
    @Autowired
    private HotelOwnerRepository ownerRepository ;
    @Override
    public Page<OwnerResponseDTO> getAllRegisteredOwners(int page, int size, String sortby, boolean ascending) {
        Sort sort = (ascending) ? Sort.by(sortby).ascending() : Sort.by(sortby).descending() ;
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<HotelOwner> retrievedOwners = ownerRepository.findAll(pageable) ;
        return retrievedOwners.map((owner)-> modelMapper.map(owner, OwnerResponseDTO.class));
    }

    @Override
    public OwnerResponseDTO getRegisteredOwnerById(String ownerId) {
        HotelOwner retreivedHotelOwner =  ownerRepository.findById(ownerId).orElseThrow(()-> new EntityNotFoundException("Hotel Owner with Id " + ownerId + " not found"));
        return modelMapper.map(retreivedHotelOwner, OwnerResponseDTO.class) ;
    }

    @Override
    public OwnerResponseDTO activateOwnerById(String ownerId) {
        HotelOwner reterivedHotelOwner =  ownerRepository.findById(ownerId).orElseThrow(()-> new EntityNotFoundException("Hotel Owner with Id " + ownerId + " not found"));
        reterivedHotelOwner.setActive(true);
        return modelMapper.map( ownerRepository.save(reterivedHotelOwner), OwnerResponseDTO.class) ;
    }

    @Override
    public OwnerResponseDTO deActivateOwnerById(String ownerId) {
        HotelOwner reterivedHotelOwner =  ownerRepository.findById(ownerId).orElseThrow(()-> new EntityNotFoundException("Hotel Owner with Id " + ownerId + " not found"));
        reterivedHotelOwner.setActive(false);
        return modelMapper.map( ownerRepository.save(reterivedHotelOwner), OwnerResponseDTO.class) ;
    }

    @Override
    public Boolean deleteOwnerById(String ownerId) {
        if( !ownerRepository.existsById(ownerId) ){
            throw new EntityNotFoundException("Hotel Owner With Id ==> " + ownerId + " Not Found");
        }
        ownerRepository.deleteById(ownerId);
        return true;
    }

    @Override
    public OwnerValidationResponseDTO validateOwner(String ownerId) {
        if( !ownerRepository.existsById(ownerId) ){
            logger.error("Hotel Owner  with ownerId ==> " + ownerId + " Not Found");
            return new OwnerValidationResponseDTO(ownerId,false);
        }
        HotelOwner hotelOwner = ownerRepository.findById(ownerId).orElse(new HotelOwner());
        if( hotelOwner.isActive() ){
            return new OwnerValidationResponseDTO(ownerId,true);
        }
        return new OwnerValidationResponseDTO(ownerId,false);
    }
}
