package com.microsercives.userservice.controllers;

import com.microsercives.userservice.dtos.response.OwnerResponseDTO;
import com.microsercives.userservice.services.HotelOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners")
public class OwnerController {
    @Autowired
    private HotelOwnerService ownerService ;
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OwnerResponseDTO>> getAllRegisteredOwners(
            @RequestParam(name = "page" , defaultValue = "0") int page ,
            @RequestParam(name = "size" , defaultValue = "5") int size ,
            @RequestParam(name = "sortby" , defaultValue = "user.firstName") String sortby ,
            @RequestParam(name = "ascending" , defaultValue = "true") boolean ascending

    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ownerService.getAllRegisteredOwners(page,size,sortby,ascending));
    }

    @GetMapping("/{ownerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OwnerResponseDTO> getRegisteredOwnerById(@PathVariable( name = "ownerId") String ownerId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ownerService.getRegisteredOwnerById(ownerId));
    }

    @PutMapping("/{ownerId}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OwnerResponseDTO> activateOwnerById(@PathVariable( name = "ownerId") String ownerId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ownerService.activateOwnerById(ownerId));
    }
    @PutMapping("/{ownerId}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OwnerResponseDTO> deActivateOwnerById(@PathVariable( name = "ownerId") String ownerId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ownerService.deActivateOwnerById(ownerId));
    }
    @DeleteMapping("/{ownerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> deleteOwnerById(@PathVariable( name = "ownerId") String ownerId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ownerService.deleteOwnerById(ownerId));
    }
}
