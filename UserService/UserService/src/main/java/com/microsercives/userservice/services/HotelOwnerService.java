package com.microsercives.userservice.services;

import com.microsercives.userservice.dtos.response.OwnerResponseDTO;
import org.springframework.data.domain.Page;

public interface HotelOwnerService {
    Page<OwnerResponseDTO> getAllRegisteredOwners(int page, int size, String sortby, boolean ascending);

    OwnerResponseDTO getRegisteredOwnerById(String ownerId);

    OwnerResponseDTO activateOwnerById(String ownerId);

    OwnerResponseDTO deActivateOwnerById(String ownerId);

    Boolean deleteOwnerById(String ownerId);
}
