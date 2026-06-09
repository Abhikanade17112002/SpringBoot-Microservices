package com.microsercives.userservice.services.impl;

import com.microsercives.userservice.dtos.response.CustomerResponseDTO;
import com.microsercives.userservice.dtos.response.CustomerValidationResponseDTO;
import com.microsercives.userservice.entities.Customer;
import com.microsercives.userservice.repositories.CustomerRepository;
import com.microsercives.userservice.services.CustomerService;

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
public class CustomerServiceImpl implements CustomerService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CustomerRepository customerRepository ;
    private final ModelMapper modelMapper ;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }


    /*
     * ========================= PAGE.MAP() EXPLANATION =========================
     *
     * retrivedCustomers is of type Page<Customer>.
     *
     * A Page is not just a collection of records like a List. It contains:
     *
     * 1. The actual data (content)
     * 2. Current page number
     * 3. Page size
     * 4. Total number of records
     * 5. Total number of pages
     * 6. Information about whether it is the first/last page
     *
     * Example:
     *
     * Page<Customer>
     * {
     *      content = [
     *          Customer(1, "Abhishek"),
     *          Customer(2, "Rahul"),
     *          Customer(3, "Priya")
     *      ],
     *      pageNumber = 0,
     *      pageSize = 10,
     *      totalElements = 50,
     *      totalPages = 5
     * }
     *
     * -------------------------------------------------------------------------
     * WHY CAN'T WE RETURN THIS DIRECTLY?
     * -------------------------------------------------------------------------
     *
     * The service method is expected to return:
     *
     *      Page<CustomerResponseDTO>
     *
     * but the repository returns:
     *
     *      Page<Customer>
     *
     * Returning entities directly to the client is not recommended because:
     *
     * - Entity may contain sensitive/internal fields.
     * - API contract should expose DTOs, not database entities.
     * - DTO allows us to control exactly what is sent in the response.
     *
     * Therefore we must convert:
     *
     *      Customer  --->  CustomerResponseDTO
     *
     * -------------------------------------------------------------------------
     * HOW DOES Page.map() HELP?
     * -------------------------------------------------------------------------
     *
     * The map() method of Page works similarly to Stream.map().
     *
     * Internally, Spring takes each Customer object present in the page content
     * and applies the provided lambda function.
     *
     * Lambda:
     *
     *      customer ->
     *          modelMapper.map(customer, CustomerResponseDTO.class)
     *
     * For every Customer:
     *
     *      Customer1 -> DTO1
     *      Customer2 -> DTO2
     *      Customer3 -> DTO3
     *
     * -------------------------------------------------------------------------
     * WHAT HAPPENS INTERNALLY?
     * -------------------------------------------------------------------------
     *
     * Conceptually, Spring performs something similar to:
     *
     *      List<CustomerResponseDTO> dtoList =
     *          retrivedCustomers.getContent()
     *                           .stream()
     *                           .map(customer ->
     *                               modelMapper.map(
     *                                   customer,
     *                                   CustomerResponseDTO.class))
     *                           .toList();
     *
     * After creating the DTO list, Spring automatically creates a new Page
     * object and copies all pagination metadata from the original page.
     *
     * -------------------------------------------------------------------------
     * BEFORE CONVERSION
     * -------------------------------------------------------------------------
     *
     * Page<Customer>
     *
     * content:
     * [Customer1, Customer2, Customer3]
     *
     * pageNumber   = 0
     * pageSize     = 10
     * totalPages   = 5
     * totalRecords = 50
     *
     * -------------------------------------------------------------------------
     * AFTER CONVERSION
     * -------------------------------------------------------------------------
     *
     * Page<CustomerResponseDTO>
     *
     * content:
     * [DTO1, DTO2, DTO3]
     *
     * pageNumber   = 0
     * pageSize     = 10
     * totalPages   = 5
     * totalRecords = 50
     *
     * -------------------------------------------------------------------------
     * IMPORTANT OBSERVATION
     * -------------------------------------------------------------------------
     *
     * Only the content type changes:
     *
     *      Customer --> CustomerResponseDTO
     *
     * Everything related to pagination remains exactly the same.
     *
     * Therefore:
     *
     *      Page<Customer>
     *              |
     *              | map()
     *              V
     *      Page<CustomerResponseDTO>
     *
     * This is why the following statement works:
     *
     *      return retrivedCustomers.map(
     *              customer ->
     *                  modelMapper.map(
     *                      customer,
     *                      CustomerResponseDTO.class));
     *
     * ========================================================================
     */
    @Override
    public Page<CustomerResponseDTO> getAllRegisteredCustomers(int page, int size, String sortby, boolean ascending) {
        Sort sort = (ascending) ? Sort.by(sortby).ascending() : Sort.by(sortby).descending() ;
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Customer> retrivedCustomers = customerRepository.findAll(pageable) ;
        return retrivedCustomers.map((customer)-> modelMapper.map(customer, CustomerResponseDTO.class));
    }

    @Override
    public CustomerResponseDTO getRegisteredCustomerById(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(
                    "Customer With Id ==> "
                            + customerId
                            + " Not Found"
            );
        }
        Customer retrievedCustomer =
                customerRepository.findById(customerId)
                        .orElse(new Customer());
        return modelMapper.map(
                retrievedCustomer,
                CustomerResponseDTO.class
        );
    }

    @Override
    public CustomerResponseDTO activateCustomerById(String customerId) {
        if( !customerRepository.existsById(customerId) ){
            throw new EntityNotFoundException("Customer With Id ==> " + customerId + " Not Found");
        }
        Customer retreivedCutomer =  customerRepository.findById(customerId).orElse(new Customer());
        retreivedCutomer.setActive(true);
        return modelMapper.map( customerRepository.save(retreivedCutomer), CustomerResponseDTO.class) ;
    }

    @Override
    public CustomerResponseDTO deActivateCustomerById(String customerId) {
        if( !customerRepository.existsById(customerId) ){
            throw new EntityNotFoundException("Customer With Id ==> " + customerId + " Not Found");
        }
        Customer retreivedCutomer =  customerRepository.findById(customerId).orElse(new Customer());
        retreivedCutomer.setActive(false);
        return modelMapper.map( customerRepository.save(retreivedCutomer), CustomerResponseDTO.class) ;
    }

    @Override
    public Boolean deleteCustomerById(String customerId) {
        if( !customerRepository.existsById(customerId) ){
            throw new EntityNotFoundException("Customer With Id ==> " + customerId + " Not Found");
        }
        customerRepository.deleteById(customerId);
        return true;
    }

    @Override
    public CustomerValidationResponseDTO validateCustomer(String customerId) {
        if( !customerRepository.existsById(customerId) ){
            logger.error("Customer with customerId ==> " + customerId + " Not Found");
            return new CustomerValidationResponseDTO(customerId,false);
        }
        Customer customer = customerRepository.findById(customerId).orElse(new Customer());
        if( customer.isActive() ){
            return new CustomerValidationResponseDTO(customerId,true);
        }
        return new CustomerValidationResponseDTO(customerId,false);
    }
}
