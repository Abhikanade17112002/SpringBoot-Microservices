package com.microsercives.userservice;

import com.microsercives.userservice.entities.Customer;
import com.microsercives.userservice.entities.User;
import com.microsercives.userservice.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Test
    public void shouldSaveNewCustomer() {
        Customer customer = new Customer();
        customer.setUser(new User());
        customer.setActive(true);
        Customer savedCustomer = customerRepository.save(customer);
        assertNotNull(savedCustomer);
    }

    @Test
    public void shouldFindCustomerById() {
        Customer customer = new Customer();
        customer.setUser(new User());
        customer.setActive(true);
        Customer savedCustomer = customerRepository.save(customer);
        Customer retreivedCustomer = customerRepository.findById(savedCustomer.getCustomerId()).orElse(null);
        assertNotNull(retreivedCustomer);
        assertEquals(savedCustomer.getCustomerId(), retreivedCustomer.getCustomerId());

    }
}
