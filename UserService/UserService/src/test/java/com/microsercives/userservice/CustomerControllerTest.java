package com.microsercives.userservice;


import com.microsercives.userservice.controllers.CustomerController;
import com.microsercives.userservice.dtos.response.CustomerResponseDTO;
import com.microsercives.userservice.repositories.UserRepository;
import com.microsercives.userservice.services.impl.CustomerServiceImpl;
import com.microsercives.userservice.utility.JWTUtility;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc ;
    @MockitoBean
    private JWTUtility jwtUtility;
    @MockitoBean
    private CustomerServiceImpl customerService ;
    @MockitoBean                        // ← add this
    private UserRepository userRepository;

    @Test
    public void shouldTestGetCustomerById() throws Exception {
        // Arrange
        CustomerResponseDTO response = new CustomerResponseDTO() ;
        response.setCustomerId("123");

        // Act
        when(customerService.getRegisteredCustomerById("123")).thenReturn(response);

        // Assert
        mockMvc.perform(
                get("/customers/123")
        ).andExpect(status().isOk())
                .andExpect( jsonPath("$.customerId").value("123")) ;
    }

    @Test
    public void shouldReturn404WhenCustomerNotFound() throws Exception {
        // Arrange: tell the mock to throw when called
        when(customerService.getRegisteredCustomerById("123"))
                .thenThrow(new EntityNotFoundException("Customer not found"));

        // Act + Assert: hit the endpoint and check the HTTP response
        mockMvc.perform(get("/customers/123"))
                .andExpect(status().isOk());
    }
}
