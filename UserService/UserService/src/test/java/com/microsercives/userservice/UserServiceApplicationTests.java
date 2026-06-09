package com.microsercives.userservice;


import com.microsercives.userservice.dtos.response.CustomerResponseDTO;
import com.microsercives.userservice.entities.Customer;
import com.microsercives.userservice.repositories.CustomerRepository;

import com.microsercives.userservice.services.impl.CustomerServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceApplicationTests {
	@Mock
	ModelMapper modelMapper;
	@Mock
	CustomerRepository customerRepository;
	@InjectMocks
	CustomerServiceImpl customerService;

	@Test
	public void shouldReturnCustomerById(){
		// Arrange
		Customer customer = new Customer();
		customer.setCustomerId("testCustomerId");

		CustomerResponseDTO responseDTO = new CustomerResponseDTO();
		responseDTO.setCustomerId("testCustomerId");

		when( customerRepository.existsById("testCustomerId")).thenReturn(true);
		when(customerRepository.findById("testCustomerId")).thenReturn(Optional.of(customer));
		when(modelMapper.map(customer, CustomerResponseDTO.class)).thenReturn(responseDTO);


		// Act

		CustomerResponseDTO response = customerService.getRegisteredCustomerById("testCustomerId");

		assertNotNull(response);
		assertEquals("testCustomerId", responseDTO.getCustomerId());

		verify(customerRepository).existsById("testCustomerId");
		verify(customerRepository).findById("testCustomerId");
		verify(modelMapper).map(customer, CustomerResponseDTO.class);
	}

	@Test
	public void shouldThrowExceptionWhenCustomerNotFound(){
		// Arrange
		when(customerRepository.existsById("testCustomerId")).thenReturn(false);

		// Act
		Exception exception = assertThrows(EntityNotFoundException.class, () -> customerService.getRegisteredCustomerById("testCustomerId"));

		String expectedMessage = "Customer With Id ==> testCustomerId Not Found";
		String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void shouldReturnAllCustomersWithPagination(){
        // Arrange
		Customer customer1 = new Customer();
		customer1.setCustomerId("testCustomerId");
		Customer customer2 = new Customer();
		customer2.setCustomerId("testCustomerId");
		CustomerResponseDTO responseDTO1 = new CustomerResponseDTO();
		responseDTO1.setCustomerId("testCustomerId");
		CustomerResponseDTO responseDTO2 = new CustomerResponseDTO();
		responseDTO2.setCustomerId("testCustomerId");
		Page<Customer> customerPage = new PageImpl<Customer>(List.of(customer1,customer2));
	    when(modelMapper.map(customer1, CustomerResponseDTO.class))
				.thenReturn(responseDTO1);
		when(modelMapper.map(customer2, CustomerResponseDTO.class))
		.thenReturn(responseDTO2);
		when(customerRepository.findAll(any(Pageable.class))).thenReturn(customerPage);

		// Act
		Page<CustomerResponseDTO> response = customerService.getAllRegisteredCustomers(0,10,"customerId",true);
		assertNotNull(response);
		assertEquals(2, response.getContent().size());
		assertEquals(responseDTO1.getCustomerId(), response.getContent().get(0).getCustomerId());
		assertEquals(responseDTO2.getCustomerId(), response.getContent().get(1).getCustomerId());
		verify(customerRepository).findAll(any(Pageable.class));
		verify(modelMapper).map(customer1, CustomerResponseDTO.class);
		verify(modelMapper).map(customer2, CustomerResponseDTO.class);

	}

//	@Override
//	public CustomerResponseDTO activateCustomerById(String customerId) {
//		if( !customerRepository.existsById(customerId) ){
//			throw new EntityNotFoundException("Customer With Id ==> " + customerId + " Not Found");
//		}
//		Customer retreivedCutomer =  customerRepository.findById(customerId).orElse(new Customer());
//		retreivedCutomer.setActive(true);
//		return modelMapper.map( customerRepository.save(retreivedCutomer), CustomerResponseDTO.class) ;
//	}

	@Test
	public void shouldReturnCustomerByIdWithStatusActive(){
		Customer customer = new Customer();
		customer.setCustomerId("testCustomerId");
		customer.setActive(false);
		CustomerResponseDTO responseDTO = new CustomerResponseDTO();
		responseDTO.setCustomerId("testCustomerId");

		when(customerRepository.existsById("testCustomerId")).thenReturn(true);
		when(customerRepository.findById("testCustomerId")).thenReturn(Optional.of(customer));
		when(modelMapper.map(customer, CustomerResponseDTO.class)).thenReturn(responseDTO);
		when(customerRepository.save(customer)).thenReturn(customer);

		// Act
		CustomerResponseDTO response = customerService.activateCustomerById("testCustomerId");
		assertNotNull(response);
		assertEquals("testCustomerId", responseDTO.getCustomerId());
		assertTrue(customer.isActive());
		verify(customerRepository,times(1)).existsById("testCustomerId");
		verify(customerRepository,times(1)).findById("testCustomerId");
		verify(customerRepository,times(1)).save(customer);

	}

	@Test
	public void shouldThrowExceptionWhenActivatingUnknownCustomer(){
		when(customerRepository.existsById("testCustomerId")).thenReturn(false);
		Exception exception = assertThrows(EntityNotFoundException.class, () -> customerService.activateCustomerById("testCustomerId"));
		String expectedMessage = "Customer With Id ==> testCustomerId Not Found";
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage, actualMessage);
		verify(customerRepository).existsById("testCustomerId");
		verify(
				customerRepository,
				never()
		).findById(anyString());
		verify(customerRepository,
			never()).save(any(Customer.class));
	}

	@Test
	public void shouldDeactivateCustomerById(){
		Customer customer = new Customer();
		customer.setCustomerId("testCustomerId");
		customer.setActive(true);
		CustomerResponseDTO responseDTO = new CustomerResponseDTO();
		responseDTO.setCustomerId("testCustomerId");
		when(customerRepository.existsById("testCustomerId")).thenReturn(true);
		when(customerRepository.findById("testCustomerId")).thenReturn(Optional.of(customer));
		when(modelMapper.map(customer, CustomerResponseDTO.class)).thenReturn(responseDTO);
		when(customerRepository.save(customer)).thenReturn(customer);

		CustomerResponseDTO response = customerService.deActivateCustomerById("testCustomerId");

		assertNotNull(response);
		assertEquals("testCustomerId", responseDTO.getCustomerId());
		assertFalse(customer.isActive());
		verify(customerRepository,times(1)).existsById("testCustomerId");
		verify(customerRepository,times(1)).findById("testCustomerId");
		verify(customerRepository,times(1)).save(customer);

	}

	@Test
	void shouldDeleteCustomer() {

		// Arrange

		when(
				customerRepository.existsById(
						"testCustomerId"
				)
		).thenReturn(true);

		// Act

		Boolean response =
				customerService.deleteCustomerById(
						"testCustomerId"
				);

		// Assert

		assertTrue(response);

		verify(customerRepository)
				.existsById(
						"testCustomerId"
				);

		verify(customerRepository)
				.deleteById(
						"testCustomerId"
				);
	}
}
