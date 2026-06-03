package com.microsercives.userservice.services.impl;


import com.microsercives.userservice.dtos.response.OwnerResponseDTO;
import com.microsercives.userservice.entities.HotelOwner;
import com.microsercives.userservice.repositories.HotelOwnerRepository;
import com.microsercives.userservice.services.HotelOwnerService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class HotelOwnerServiceImpl implements HotelOwnerService {
    @Autowired
    private ModelMapper modelMapper ;
    @Autowired
    private HotelOwnerRepository ownerRepository ;
    @Override
    public Page<OwnerResponseDTO> getAllRegisteredOwners(int page, int size, String sortby, boolean ascending) {
        Sort sort = (ascending) ? Sort.by(sortby).ascending() : Sort.by(sortby).descending() ;
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<HotelOwner> retrivedOwners = ownerRepository.findAll(pageable) ;
        return retrivedOwners.map((owner)-> modelMapper.map(owner, OwnerResponseDTO.class));
    }

    @Override
    public OwnerResponseDTO getRegisteredOwnerById(String ownerId) {
        if( !ownerRepository.existsById(ownerId) ){
            throw new EntityNotFoundException("Hotel Owner  With Id ==> " + ownerId + " Not Found");
        }
        HotelOwner retreivedHotelOwner =  ownerRepository.findById(ownerId).orElse(new HotelOwner());
        return modelMapper.map(retreivedHotelOwner, OwnerResponseDTO.class) ;
    }

    @Override
    public OwnerResponseDTO activateOwnerById(String ownerId) {
        if( !ownerRepository.existsById(ownerId) ){
            throw new EntityNotFoundException("Hotel Owner With Id ==> " + ownerId + " Not Found");
        }
        HotelOwner reterivedHotelOwner =  ownerRepository.findById(ownerId).orElse(new HotelOwner());
        reterivedHotelOwner.setActive(true);
        return modelMapper.map( ownerRepository.save(reterivedHotelOwner), OwnerResponseDTO.class) ;
    }

    @Override
    public OwnerResponseDTO deActivateOwnerById(String ownerId) {
        if( !ownerRepository.existsById(ownerId) ){
            throw new EntityNotFoundException("Hotel Owner With Id ==> " + ownerId + " Not Found");
        }
        HotelOwner reterivedHotelOwner =  ownerRepository.findById(ownerId).orElse(new HotelOwner());
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
}
