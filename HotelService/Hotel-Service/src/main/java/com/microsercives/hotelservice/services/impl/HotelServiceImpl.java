package com.microsercives.hotelservice.services.impl;

import com.microsercives.hotelservice.dtos.request.CreateHotelRequestDTO;
import com.microsercives.hotelservice.dtos.request.UpdateHotelRequestDTO;
import com.microsercives.hotelservice.dtos.response.HotelImageResponseDTO;
import com.microsercives.hotelservice.dtos.response.HotelResponseDTO;
import com.microsercives.hotelservice.dtos.response.HotelValidationResponseDTO;
import com.microsercives.hotelservice.dtos.response.ListOfHotelOwnerHotelIdsListResponseDTO;
import com.microsercives.hotelservice.entities.AuthenticatedUser;
import com.microsercives.hotelservice.entities.Hotel;
import com.microsercives.hotelservice.entities.HotelImage;
import com.microsercives.hotelservice.repositories.HotelImageRepository;
import com.microsercives.hotelservice.repositories.HotelRepositories;
import com.microsercives.hotelservice.services.HotelOwnerVerificationService;
import com.microsercives.hotelservice.services.HotelService;
import com.microsercives.hotelservice.utility.ImageValidationUtility;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private HotelRepositories repositories;
    @Autowired
    private HotelOwnerVerificationService verificationService;
    private Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);
    private final AwsS3FileStorage awsS3FileStorage ;
    private final String AWS_REGION;
    private final String AWS_BUCKET_NAME;
    private final HotelRepositories hotelRepositories;
    private final HotelImageRepository hotelImageRepository;

    public HotelServiceImpl(AwsS3FileStorage awsS3FileStorage, @Value("${aws.region}") String AWS_REGION, @Value("${aws.s3.bucket-name}") String AWS_BUCKET_NAME, HotelRepositories hotelRepositories, HotelImageRepository hotelImageRepository) {
        this.awsS3FileStorage = awsS3FileStorage;
        this.AWS_REGION = AWS_REGION;
        this.AWS_BUCKET_NAME = AWS_BUCKET_NAME;
        this.hotelRepositories = hotelRepositories;
        this.hotelImageRepository = hotelImageRepository;
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

    @Override
    public HotelResponseDTO addHotelImage(MultipartFile image, String hotelId) {
        Hotel reterivedHotel = hotelRepositories.findById(hotelId).orElseThrow(()-> new EntityNotFoundException("Hotel With Id " + hotelId + " Not Found"));
        logger.info("Reterived Hotel{}", reterivedHotel);
        HotelValidationResponseDTO validationResponse = validateHotel(reterivedHotel.getHotelId());

        if(!validationResponse.getActive()){
            throw new RuntimeException("Hotel With Id " + hotelId + " Not Active");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

        if( user.getRole().equalsIgnoreCase("OWNER") && !user.getUserId().equalsIgnoreCase(reterivedHotel.getOwnerId())){
            throw new RuntimeException("Hotel With Id " + hotelId + " Not Active");
        }

        ImageValidationUtility.validate(image);

        String extension = getExtension(image);
        logger.info("Extension ==> {}",extension);

        String objectKey =getHotelImageObjectKey(hotelId,extension);
        awsS3FileStorage.upload(image, objectKey);
        final String imagePublicUrl = getHotelPublicImageUrl(objectKey);
        HotelImage savedHotelImage = getHotelImageEntity(reterivedHotel,objectKey,image.getOriginalFilename(),image.getContentType(), image.getSize(), imagePublicUrl);
        reterivedHotel.getHotelImages().add(savedHotelImage);
        Hotel savedHotel = hotelRepositories.save(reterivedHotel);
        HotelResponseDTO response = modelMapper.map(savedHotel, HotelResponseDTO.class);
        List<HotelImageResponseDTO> hotelImagesResponseDTOList = savedHotel.getHotelImages().stream().map((h)->modelMapper.map(h, HotelImageResponseDTO.class)).toList() ;
        response.setHotelImages(hotelImagesResponseDTOList);

        return response;
    }

    @Override
    public HotelResponseDTO getHotelImages(String hotelId) {
        Hotel reterivedHotel = hotelRepositories.findById(hotelId).orElseThrow(()-> new EntityNotFoundException("Hotel With Id " + hotelId + " Not Found"));
        HotelResponseDTO response = modelMapper.map(reterivedHotel, HotelResponseDTO.class);
        List<HotelImageResponseDTO> hotelImagesResponseDTOList = reterivedHotel.getHotelImages().stream().map((h)->modelMapper.map(h, HotelImageResponseDTO.class)).toList() ;
        response.setHotelImages(hotelImagesResponseDTOList);
        return response;
    }

    @Override
    public HotelResponseDTO deleteHotelImageWithId(String hotelId, String imageId) {
        Hotel reterivedHotel = hotelRepositories.findById(hotelId).orElseThrow(()-> new EntityNotFoundException("Hotel With Id " + hotelId + " Not Found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

        if( user.getRole().equalsIgnoreCase("OWNER") && !user.getUserId().equalsIgnoreCase(reterivedHotel.getOwnerId())){
            throw new RuntimeException("Hotel With Id " + hotelId + " Not Active");
        }

        List<HotelImage> updatedHotelImagesList = reterivedHotel
                .getHotelImages()
                .stream()
                .filter((hotelImage)-> !hotelImage.getImageId().equalsIgnoreCase(imageId)).toList();

        List<HotelImage> filteredHotelImage =  reterivedHotel
                .getHotelImages()
                .stream()
                .filter((hotelImage)-> hotelImage.getImageId().equalsIgnoreCase(imageId)).toList();
        awsS3FileStorage.delete(filteredHotelImage.get(0).getObjectKey());
        logger.info("Update List Before {}", updatedHotelImagesList);
        if( filteredHotelImage.get(0).isPrimaryImage() ){
            updatedHotelImagesList.get(0).setPrimaryImage(true);
        }
        logger.info("Update List After {}", updatedHotelImagesList);

        reterivedHotel.setHotelImages(updatedHotelImagesList);
        Hotel savedHotel = hotelRepositories.save(reterivedHotel);
        HotelResponseDTO response = modelMapper.map(savedHotel, HotelResponseDTO.class);
        List<HotelImageResponseDTO> hotelImagesResponseDTOList = savedHotel.getHotelImages().stream().map((h)->modelMapper.map(h, HotelImageResponseDTO.class)).toList() ;
        response.setHotelImages(hotelImagesResponseDTOList);

        return response;
    }

    @Override
    public HotelResponseDTO setHotelImageWithIdAsPrimary(String hotelId, String imageId) {
        Hotel reterivedHotel = hotelRepositories.findById(hotelId).orElseThrow(()-> new EntityNotFoundException("Hotel With Id " + hotelId + " Not Found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

        if( user.getRole().equalsIgnoreCase("OWNER") && !user.getUserId().equalsIgnoreCase(reterivedHotel.getOwnerId())){
            throw new RuntimeException("Hotel With Id " + hotelId + " Not Active");
        }

        List<HotelImage> updatedHotelImagesList = reterivedHotel
                .getHotelImages()
                .stream()
                .map((hotelImage)-> {
                    if( hotelImage.getImageId().equalsIgnoreCase(imageId) && !hotelImage.isPrimaryImage()){
                        hotelImage.setPrimaryImage(true);
                        return hotelImage ;
                    } else if (!hotelImage.getImageId().equalsIgnoreCase(imageId) && hotelImage.isPrimaryImage()) {
                        hotelImage.setPrimaryImage(false);
                        return hotelImage ;
                    }
                    else{
                        return hotelImage ;
                    }
                }).collect(Collectors.toList());

        logger.info("Update List After {}", updatedHotelImagesList);

        reterivedHotel.setHotelImages(updatedHotelImagesList);
        Hotel savedHotel = hotelRepositories.save(reterivedHotel);
        HotelResponseDTO response = modelMapper.map(savedHotel, HotelResponseDTO.class);
        List<HotelImageResponseDTO> hotelImagesResponseDTOList = savedHotel.getHotelImages().stream().map((h)->modelMapper.map(h, HotelImageResponseDTO.class)).toList() ;
        response.setHotelImages(hotelImagesResponseDTOList);
        return response;
    }

    private String getExtension(MultipartFile file) {

        String contentType = file.getContentType();

        return switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> throw new IllegalArgumentException(
                    "Unsupported image type"
            );
        };
    }

    private String getHotelPublicImageUrl ( String objectKey ){
        return "https://" +  AWS_BUCKET_NAME + ".s3." + AWS_REGION + ".amazonaws.com/" + objectKey;
    }
    private String getHotelImageObjectKey ( String hotelId , String extension ){
        return  "hotels/" + hotelId + "/" + UUID.randomUUID()  + extension;
    }
    private HotelImage getHotelImageEntity(     Hotel reterivedHotel,String objectKey,String originalFileName ,String contentType , Long fileSize , String imagePublicUrl){
        HotelImage hotelImage = new HotelImage();
        hotelImage.setHotel(reterivedHotel);
        hotelImage.setObjectKey(objectKey);
        hotelImage.setOriginalFileName(originalFileName);
        hotelImage.setContentType(contentType);
        hotelImage.setFileSize(fileSize);
        hotelImage.setImagePublicUrl(imagePublicUrl);
        long existingImages = hotelImageRepository.countByHotelId(reterivedHotel);
        hotelImage.setDisplayOrder((int) existingImages + 1);
        hotelImage.setPrimaryImage(existingImages == 0);
        return hotelImage;
    }

}