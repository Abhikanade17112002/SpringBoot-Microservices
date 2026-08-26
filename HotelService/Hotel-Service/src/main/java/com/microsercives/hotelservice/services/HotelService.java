package com.microsercives.hotelservice.services;

import com.microsercives.hotelservice.dtos.request.CreateHotelRequestDTO;
import com.microsercives.hotelservice.dtos.request.UpdateHotelRequestDTO;
import com.microsercives.hotelservice.dtos.response.HotelResponseDTO;
import com.microsercives.hotelservice.dtos.response.HotelValidationResponseDTO;
import com.microsercives.hotelservice.dtos.response.ListOfHotelOwnerHotelIdsListResponseDTO;
import com.microsercives.hotelservice.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HotelService {

    // Create
    HotelResponseDTO createHotel(CreateHotelRequestDTO createHotelRequestDTO);


    Page<HotelResponseDTO> getAllHotels(int page, int size, String sortby, Boolean ascending);

    HotelResponseDTO getHotelById(String hotelId);

    HotelResponseDTO updateHotelById(String hotelId, UpdateHotelRequestDTO updateHotelRequestDTO);

    void deleteHotelById(String hotelId);

    Page<HotelResponseDTO> findByHotelNameContainingIgnoreCase(String hotelName, int page, int size, String sortby, Boolean ascending);

    Page<HotelResponseDTO> findByLocationContainingIgnoreCase(String location, int page, int size, String sortby, Boolean ascending);

    Page<HotelResponseDTO> findHotelsByOwnerId(String ownerId, int page, int size, String sortby, Boolean ascending);

    HotelValidationResponseDTO validateHotel(String hotelId);

    ListOfHotelOwnerHotelIdsListResponseDTO getOwnerHotelsIdList(String ownerId);

    HotelResponseDTO addHotelImage(MultipartFile image,String hotelId);

    HotelResponseDTO getHotelImages(String hotelId);

    HotelResponseDTO deleteHotelImageWithId(String hotelId, String imageId);

    HotelResponseDTO setHotelImageWithIdAsPrimary(String hotelId, String imageId);
}