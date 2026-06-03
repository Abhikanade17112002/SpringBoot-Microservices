package com.microsercives.userservice.services;

import com.microsercives.userservice.dtos.response.CustomerResponseDTO;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Page<CustomerResponseDTO> getAllRegisteredCustomers(int page, int size, String sortby, boolean ascending);
    CustomerResponseDTO getRegisteredCustomerById(String customerId);
    CustomerResponseDTO activateCustomerById(String customerId);
    CustomerResponseDTO deActivateCustomerById(String customerId);

    Boolean deleteCustomerById(String customerId);
}
