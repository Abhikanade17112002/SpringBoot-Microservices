package com.microsercives.hotelservice.controllers;

import com.microsercives.hotelservice.dtos.request.CreateHotelRequestDTO;
import com.microsercives.hotelservice.dtos.response.HotelResponseDTO;
import com.microsercives.hotelservice.dtos.request.UpdateHotelRequestDTO;
import com.microsercives.hotelservice.services.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/hotels")
public class HotelControllers {

    private final HotelService hotelService;

    private final ModelMapper modelMapper;

    public HotelControllers(HotelService hotelService,
                            ModelMapper modelMapper) {
        this.hotelService = hotelService;
        this.modelMapper = modelMapper;
    }


    // CREATE HOTEL
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<HotelResponseDTO> createHotel(@RequestBody CreateHotelRequestDTO createHotelRequestDTO) {
        System.out.println("CALLING CREATE HOTEL");
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hotelService.createHotel(createHotelRequestDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<HotelResponseDTO>> getAllHotels(@RequestParam(name = "page" ,defaultValue = "0") int page , @RequestParam(name = "size", defaultValue = "5") int size , @RequestParam(name = "sortby" , defaultValue = "hotelName")  String sortby, @RequestParam(name = "ascending" , defaultValue = "true") Boolean ascending ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hotelService.getAllHotels(page,size,sortby,ascending));
    }

    // GET HOTEL BY ID
    @GetMapping("/{hotelId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<HotelResponseDTO> getHotelById(@PathVariable(name = "hotelId") String hotelId) {
     return ResponseEntity
             .status(HttpStatus.OK)
             .body(hotelService.getHotelById(hotelId));
    }

    // UPDATE HOTEL
    @PutMapping("/{hotelId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<HotelResponseDTO> updateHotelById(@PathVariable String hotelId,@RequestBody UpdateHotelRequestDTO updateHotelRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hotelService.updateHotelById(hotelId,updateHotelRequestDTO));
    }

    // DELETE HOTEL
    @DeleteMapping("/{hotelId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<Void> deleteHotelById(@PathVariable String hotelId) {
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/by-name")
    public ResponseEntity<Page<HotelResponseDTO>> findByHotelNameContainingIgnoreCase(@RequestParam( name ="hotelname" , defaultValue = "") String hotelName , @RequestParam(name = "page" ,defaultValue = "0") int page , @RequestParam(name = "size", defaultValue = "5") int size , @RequestParam(name = "sortby" , defaultValue = "hotelName")  String sortby, @RequestParam(name = "ascending" , defaultValue = "true") Boolean ascending ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hotelService.findByHotelNameContainingIgnoreCase(hotelName,page,size,sortby,ascending));
    }
    @GetMapping("/search/by-location")
    public ResponseEntity<Page<HotelResponseDTO>> findByLocationContainingIgnoreCase(@RequestParam( name ="location" , defaultValue = "") String location , @RequestParam(name = "page" ,defaultValue = "0") int page , @RequestParam(name = "size", defaultValue = "5") int size , @RequestParam(name = "sortby" , defaultValue = "location")  String sortby, @RequestParam(name = "ascending" , defaultValue = "true") Boolean ascending ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hotelService.findByLocationContainingIgnoreCase(location,page,size,sortby,ascending));
    }
    @GetMapping("/search/by-owner/{ownerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<HotelResponseDTO>> findHotelsByOwnerId(@PathVariable( name ="ownerId" ) String ownerId , @RequestParam(name = "page" ,defaultValue = "0") int page , @RequestParam(name = "size", defaultValue = "5") int size , @RequestParam(name = "sortby" , defaultValue = "hotelName")  String sortby, @RequestParam(name = "ascending" , defaultValue = "true") Boolean ascending ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hotelService.findHotelsByOwnerId(ownerId,page,size,sortby,ascending));
    }

    @PostMapping(value = "/{hotelId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<HotelResponseDTO> addHotelImage(@RequestPart("image") MultipartFile image,@PathVariable(name = "hotelId") String hotelId){
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(hotelService.addHotelImage(image,hotelId));
    }

    @GetMapping(value = "/{hotelId}/images")
    public ResponseEntity<HotelResponseDTO> getHotelImages(@PathVariable(name = "hotelId") String hotelId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hotelService.getHotelImages(hotelId));
    }

    @DeleteMapping(value = "/{hotelId}/{imageId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<HotelResponseDTO> deleteHotelImageWithId(@PathVariable(name = "hotelId") String hotelId , @PathVariable(name = "imageId") String imageId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hotelService.deleteHotelImageWithId(hotelId,imageId));
    }

    @PutMapping(value = "/{hotelId}/{imageId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<HotelResponseDTO> setHotelImageWithIdAsPrimary(@PathVariable(name = "hotelId") String hotelId , @PathVariable(name = "imageId") String imageId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hotelService.setHotelImageWithIdAsPrimary(hotelId,imageId));
    }

}