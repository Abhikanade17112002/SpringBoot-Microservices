package com.microsercives.userservice.controllers;


import com.microsercives.userservice.dtos.UserResponseDTO;
import com.microsercives.userservice.dtos.response.CustomerResponseDTO;
import com.microsercives.userservice.repositories.CustomerRepository;
import com.microsercives.userservice.services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerSevice ;
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<CustomerResponseDTO>> getAllRegisteredCustomers(
            @RequestParam(name = "page" , defaultValue = "0") int page ,
            @RequestParam(name = "size" , defaultValue = "5") int size ,
            @RequestParam(name = "sortby" , defaultValue = "user.firstName") String sortby ,
            @RequestParam(name = "ascending" , defaultValue = "true") boolean ascending, HttpServletRequest request, HttpServletResponse response){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerSevice.getAllRegisteredCustomers(page,size,sortby,ascending));
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponseDTO> getRegisteredCustomerById(@PathVariable( name = "customerId") String customerId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerSevice.getRegisteredCustomerById(customerId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{customerId}/activate")
    public ResponseEntity<CustomerResponseDTO> activateCustomerById(@PathVariable( name = "customerId") String customerId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerSevice.activateCustomerById(customerId));
    }
    @PutMapping("/{customerId}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponseDTO> deActivateCustomerById(@PathVariable( name = "customerId") String customerId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerSevice.deActivateCustomerById(customerId));
    }
    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> deleteCustomerById(@PathVariable( name = "customerId") String customerId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerSevice.deleteCustomerById(customerId));
    }

}
