package com.microsercives.userservice;

import com.microsercives.userservice.dtos.response.CustomerResponseDTO;
import com.microsercives.userservice.dtos.response.CustomerValidationResponseDTO;
import com.microsercives.userservice.entities.Customer;
import com.microsercives.userservice.entities.User;
import com.microsercives.userservice.repositories.CustomerRepository;
import com.microsercives.userservice.services.impl.CustomerServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.WeakHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class CustomerServiceLayerTest {
    @Mock
    private CustomerRepository customerRepository ;
    @Mock
    private ModelMapper modelMapper ;
    @InjectMocks
    private CustomerServiceImpl customerService ;

    @Test
    public void shoulReturnCustomerWhenExists(){
        Customer customer = new Customer();
        customer.setActive(true);
        customer.setCustomerId("123");

        CustomerResponseDTO response = new CustomerResponseDTO();
        response.setCustomerId("123");
        response.setActive(true);
        response.setUser(new User());

        when(customerRepository.existsById("123"))
                .thenReturn(true);
        when(customerRepository.findById("123"))
                .thenReturn(Optional.of(customer));
        when(modelMapper.map(customer,CustomerResponseDTO.class))
                .thenReturn(response);

        // Act
        CustomerResponseDTO responseDTO = customerService.getRegisteredCustomerById("123") ;

        assertNotNull(responseDTO);
        assertTrue(responseDTO.isActive());
        assertEquals("123",responseDTO.getCustomerId());
        verify(customerRepository,times(1)).existsById("123");
        verify(customerRepository,times(1)).findById("123");

    }

    @Test
    public void shouldThrowExceptionWhenCustomerDoesNotExists(){
        when(customerRepository.existsById("123")).thenReturn(false);
        Exception exception = assertThrows(EntityNotFoundException.class,()->customerService.getRegisteredCustomerById("123"));
        assertNotNull(exception);
        assertEquals( "Customer With Id ==> "+  "123"  + " Not Found",exception.getMessage());
    }

    @Test
    public void shouldReturnAllThePaginatedCustomers(){
        Customer customer1 = new Customer() ;
        customer1.setCustomerId("1");
        customer1.setActive(true);


        Customer customer2 = new Customer() ;
        customer2.setCustomerId("2");
        customer2.setActive(true);

        Customer customer3 = new Customer() ;
        customer3.setCustomerId("3");
        customer3.setActive(true);

        CustomerResponseDTO responseDTO1 = new CustomerResponseDTO();
        responseDTO1.setCustomerId("1");
        responseDTO1.setActive(true);
        CustomerResponseDTO responseDTO2 = new CustomerResponseDTO();
        responseDTO2.setCustomerId("2");
        CustomerResponseDTO responseDTO3 = new CustomerResponseDTO();
        responseDTO3.setCustomerId("3");
        Page pageable = new PageImpl<>(List.of(customer1,customer2,customer3));

        when(customerRepository.findAll(any(Pageable.class)))
                .thenReturn(pageable);
        when(modelMapper.map(customer1,CustomerResponseDTO.class))
                .thenReturn(responseDTO1);
        when(modelMapper.map(customer2,CustomerResponseDTO.class))
                .thenReturn(responseDTO2);
        when(modelMapper.map(customer3,CustomerResponseDTO.class))
                .thenReturn(responseDTO3);

        Page<CustomerResponseDTO> response = customerService.getAllRegisteredCustomers(0,5,"customerId",true) ;

        assertNotNull(response);
        assertEquals(3,response.getContent().size());
        assertEquals("1",response.getContent().get(0).getCustomerId());
        assertEquals(true,response.getContent().get(0).isActive());
        assertEquals("2",response.getContent().get(1).getCustomerId());
        assertEquals("3",response.getContent().get(2).getCustomerId());


    }

    @Test
    public void shouldActivateCustomerById(){
        Customer customer = new Customer() ;
        customer.setActive(false);
        customer.setCustomerId("123");

        CustomerResponseDTO responseDTO = new CustomerResponseDTO() ;
        responseDTO.setCustomerId("123");

        when(customerRepository.existsById("123")).thenReturn(true);
        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        when(modelMapper.map(customer,CustomerResponseDTO.class)).thenReturn(responseDTO);
        when(customerRepository.save(customer)).thenReturn(customer);

        CustomerResponseDTO response = customerService.activateCustomerById("123") ;

        assertEquals("123",responseDTO.getCustomerId());
        assertTrue(customer.isActive());
    }

    @Test
    public void shouldDeleteCustomerById(){
        when(
                customerRepository.existsById("123")
        )
                .thenReturn(true);

        boolean res = customerService.deleteCustomerById("123");
        verify(customerRepository, times(1))
                .deleteById("123");
        assertTrue(res);
    }

    @Test
    public void shouldReturnInValidWhenCustomerIsInActive(){
        Customer customer = new Customer();
        customer.setActive(false);
        customer.setCustomerId("123");
        when(customerRepository.existsById("123")).thenReturn(true);
        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));

        CustomerValidationResponseDTO response = customerService.validateCustomer("123");

        assertFalse(response.getActive());
        assertEquals("123",response.getCustomerId());

    }

    @Test
    public void shouldCallRepositoryOnceWhileFetchingCustomer(){
        Customer customer = new Customer();
        customer.setCustomerId("123");
        customer.setActive(true) ;

        CustomerResponseDTO responseDTO = new CustomerResponseDTO() ;


        when( customerRepository.existsById("123")).thenReturn(true);
        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        when(modelMapper.map(customer,CustomerResponseDTO.class)).thenReturn(responseDTO);

        CustomerResponseDTO response = customerService.getRegisteredCustomerById("123");

        verify(customerRepository,times(1)).existsById("123");
        verify(customerRepository,times(1)).findById("123");
        verify(modelMapper,times(1)).map(customer,CustomerResponseDTO.class);
    }


    @Test
    public void shouldNotCallFindByIdWhenCustomerDoesNotExist(){


        when( customerRepository.existsById("123")).thenReturn(false);

 Exception exception = assertThrows(EntityNotFoundException.class,()->customerService.getRegisteredCustomerById("123"));

        verify(customerRepository,times(1)).existsById("123");
        verify(customerRepository,never()).findById("123");
        assertEquals( "Customer With Id ==> "+  "123"  + " Not Found",exception.getMessage());
    }
}
