package com.microsercives.userservice;


import com.microsercives.userservice.controllers.CustomerController;
import com.microsercives.userservice.controllers.InternalController;
import com.microsercives.userservice.dtos.response.CustomerResponseDTO;
import com.microsercives.userservice.dtos.response.CustomerValidationResponseDTO;
import com.microsercives.userservice.entities.Customer;
import com.microsercives.userservice.external.services.hotelservice.HotelService;
import com.microsercives.userservice.repositories.UserRepository;
import com.microsercives.userservice.services.CustomerService;
import com.microsercives.userservice.services.UserService;
import com.microsercives.userservice.services.impl.HotelOwnerServiceImpl;
import com.microsercives.userservice.utility.JWTUtility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
//@WebMvcTest(InternalController.class)
@WebMvcTest(controllers = CustomerController.class)
//@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CustomerService customerService;
    @MockitoBean
    private JWTUtility jwtUtility;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private HotelOwnerServiceImpl hotelService;

    @Test
    public void shouldReturnCustomerWithId() throws Exception {
        // Arrange
        CustomerResponseDTO response =  new CustomerResponseDTO();
        response.setCustomerId("123");

        when(customerService.getRegisteredCustomerById(anyString())).thenReturn(response);

        mockMvc.perform(
                get("/customers/123")
        ).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.customerId").value("123"));

    }

    @Test void shouldThrowExceptionWhenCustomerNotFound() throws Exception {
        when( customerService.getRegisteredCustomerById(anyString())).thenThrow(HttpServerErrorException.InternalServerError.class);
        mockMvc.perform(
                get("/customers/123")
        )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldReturnAllCustomers() throws Exception {
        // Arrange
        CustomerResponseDTO customer1 =  new CustomerResponseDTO();
        customer1.setCustomerId("1");
        CustomerResponseDTO customer2 =  new CustomerResponseDTO();
        customer2.setCustomerId("2");
        CustomerResponseDTO customer3 =  new CustomerResponseDTO();
        customer3.setCustomerId("3");

        Page<CustomerResponseDTO> page = new PageImpl<>(Arrays.asList(customer1, customer2, customer3));

        when(
                customerService.getAllRegisteredCustomers(0,5,"user.firstName",true)
        )
        .thenReturn(page);

        mockMvc.perform(
                get("/customers")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].customerId").value("1"))
                .andExpect(jsonPath("$.content[1].customerId").value("2"))
                .andExpect(jsonPath("$.content[2].customerId").value("3"));


    }

    @Test
    public void shouldReturnPagedCustomer()  throws Exception {
        // Arrange
        CustomerResponseDTO customer1 =  new CustomerResponseDTO();
        customer1.setCustomerId("1");
        CustomerResponseDTO customer2 =  new CustomerResponseDTO();
        customer2.setCustomerId("2");
        CustomerResponseDTO customer3 =  new CustomerResponseDTO();
        customer3.setCustomerId("3");

        Page<CustomerResponseDTO> page = new PageImpl<>(Arrays.asList(customer1, customer2, customer3));

        when(
                customerService.getAllRegisteredCustomers(0,5,"user.firstName",true)
        )
                .thenReturn(page);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].customerId").value("1"))
                .andExpect(jsonPath("$.content[1].customerId").value("2"))
                .andExpect(jsonPath("$.content[2].customerId").value("3"))
                .andExpect(jsonPath("$.content.length()").value(3));




    }


    @Test
    public void shouldReturnEmptyCustomerPage()  throws Exception {
        Page<CustomerResponseDTO> page = new PageImpl<>(Arrays.asList());

        when(
                customerService.getAllRegisteredCustomers(0,5,"user.firstName",true)
        ).thenReturn(page);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(0));
    }

    @Test
    public void shouldActivateCustomer() throws Exception {
       CustomerResponseDTO customer =  new CustomerResponseDTO();
       customer.setCustomerId("1");
       customer.setActive(true);

       when(customerService.activateCustomerById(anyString())).thenReturn(customer);

       mockMvc.perform(
               put("/customers/1/activate")
       )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.customerId").value("1"))
               .andExpect(jsonPath("$.active").value(true));

       verify(customerService, times(1)).activateCustomerById(anyString());
    }

    @Test
    public void shouldDeleteTheCustomer() throws Exception {
        when(customerService.deleteCustomerById(anyString())).thenReturn(true);

        mockMvc.perform(
                delete("/customers/1")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
        .andExpect(content().contentType("application/json"));

        verify(customerService, times(1)).deleteCustomerById(anyString());
    }


    @Test
    public void shouldValidateActiveCustomer() throws Exception {
        CustomerValidationResponseDTO customer =   new CustomerValidationResponseDTO();
        customer.setActive(true);
        customer.setCustomerId("1");

        when(customerService.validateCustomer("1"))
        .thenReturn(customer);

        mockMvc.perform(get("/internal/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("1"))
                .andExpect(jsonPath("$.active").value(true));

        verify(customerService, times(1)).validateCustomer("1");

    }
    @Test
    void shouldReturn401WhenUnauthorized() throws Exception {

        mockMvc.perform(
                        put("/customers/1/activate")
                                .with(csrf())
                )
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn403ForWrongRole() throws Exception {

        mockMvc.perform(
                        put("/customers/1/activate")
                )
                .andExpect(status().isForbidden());
    }
}


/**
 Excellent approach.

 Instead of random tests, let's design a **complete testing curriculum** around your `Customer` module.

 Think of it like this:

 ```text id="jew8fc"
 CustomerControllerTest     -> 20 tests
 CustomerServiceTest        -> 20 tests
 CustomerRepositoryTest     -> 20 tests
 CustomerIntegrationTest    -> 20 tests (@SpringBootTest)
 ```

 By the end you'll understand almost every testing concept used in Spring Boot projects.

 ---

 # PHASE 1 : CONTROLLER LAYER (@WebMvcTest)

 ## What are we testing?

 ```text id="hd9wn0"
 URL Mapping

 Request Parameters

 Path Variables

 JSON Request Body

 HTTP Status Codes

 Validation

 Security
 ```

 ---

 ## GET APIs

 ### 1

 ```text id="ly4a2r"
 shouldReturnCustomerById
 ```

 Verify:

 ```text id="0bq57d"
 200 OK
 Correct JSON
 ```

 ---

 ### 2

 ```text id="l62y65"
 shouldReturn404WhenCustomerNotFound
 ```

 Verify:

 ```text id="m7p6n8"
 404 NOT FOUND
 ```

 ---

 ### 3

 ```text id="j0s9g3"
 shouldReturnAllCustomers
 ```

 Verify:

 ```text id="5rxuvh"
 List returned
 ```

 ---

 ### 4

 ```text id="p5v4mu"
 shouldReturnPagedCustomers
 ```

 Verify:

 ```text id="s6yewm"
 Pagination parameters
 ```

 ---

 ### 5

 ```text id="a8iwbr"
 shouldReturnEmptyCustomerPage
 ```

 ---

 ## PATCH / PUT APIs

 ### 6

 ```text id="ah6kx5"
 shouldActivateCustomer
 ```

 ---

 ### 7

 ```text id="3my7jg"
 shouldDeactivateCustomer
 ```

 ---

 ### 8

 ```text id="d9iy3m"
 shouldReturn404WhenActivatingUnknownCustomer
 ```

 ---

 ### 9

 ```text id="q3mmum"
 shouldReturn404WhenDeactivatingUnknownCustomer
 ```

 ---

 ## DELETE APIs

 ### 10

 ```text id="edrl03"
 shouldDeleteCustomer
 ```

 ---

 ### 11

 ```text id="y5h6y0"
 shouldReturn404WhenDeletingUnknownCustomer
 ```

 ---

 ## INTERNAL APIs

 ### 12

 ```text id="4kwy8c"
 shouldValidateActiveCustomer
 ```

 ---

 ### 13

 ```text id="5y6c0g"
 shouldValidateInactiveCustomer
 ```

 ---

 ### 14

 ```text id="6b4qel"
 shouldReturnInvalidForUnknownCustomer
 ```

 ---

 ## Validation Tests

 ### 15

 ```text id="0dix7i"
 shouldReturn400WhenCustomerIdMissing
 ```

 ---

 ### 16

 ```text id="yjz63q"
 shouldReturn400ForInvalidPathVariable
 ```

 ---

 ## Security Tests

 ### 17

 ```text id="f0sq9j"
 shouldReturn401WhenUnauthorized
 ```

 ---

 ### 18

 ```text id="xtpnca"
 shouldReturn403ForWrongRole
 ```

 ---

 ## Exception Handler Tests

 ### 19

 ```text id="b13ctm"
 shouldReturnProperErrorResponseForEntityNotFound
 ```

 ---

 ### 20

 ```text id="2qqrpx"
 shouldReturnInternalServerErrorForUnexpectedException
 ```

 ---

 # PHASE 2 : SERVICE LAYER (Mockito)

 ## What are we testing?

 ```text id="6m69x7"
 Business Logic
 Decision Making
 Repository Interaction
 ```

 ---

 ### 1

 ```text id="hv0cx8"
 shouldReturnCustomerWhenCustomerExists
 ```

 ---

 ### 2

 ```text id="5jup71"
 shouldThrowExceptionWhenCustomerDoesNotExist
 ```

 ---

 ### 3

 ```text id="c4v0vi"
 shouldReturnAllCustomersWithPagination
 ```

 ---

 ### 4

 ```text id="03h7cc"
 shouldActivateCustomer
 ```

 ---

 ### 5

 ```text id="8kpp0u"
 shouldThrowExceptionWhenActivatingUnknownCustomer
 ```

 ---

 ### 6

 ```text id="4h2jlwm"
 shouldDeactivateCustomer
 ```

 ---

 ### 7

 ```text id="db3t4z"
 shouldThrowExceptionWhenDeactivatingUnknownCustomer
 ```

 ---

 ### 8

 ```text id="ewxvny"
 shouldDeleteCustomer
 ```

 ---

 ### 9

 ```text id="dt0rxl"
 shouldThrowExceptionWhenDeletingUnknownCustomer
 ```

 ---

 ### 10

 ```text id="0g3d8g"
 shouldReturnValidWhenCustomerIsActive
 ```

 ---

 ### 11

 ```text id="zhb5y2"
 shouldReturnInvalidWhenCustomerIsInactive
 ```

 ---

 ### 12

 ```text id="ub3k70"
 shouldReturnInvalidWhenCustomerDoesNotExist
 ```

 ---

 ### 13

 ```text id="e8on5g"
 shouldCallRepositoryOnceWhileFetchingCustomer
 ```

 Learn:

 ```text id="5lt63g"
 times(1)
 ```

 ---

 ### 14

 ```text id="3ubvvn"
 shouldNotCallFindByIdWhenCustomerDoesNotExist
 ```

 Learn:

 ```text id="4e1mdp"
 never()
 ```

 ---

 ### 15

 ```text id="e3mkrv"
 shouldNotCallMapperWhenCustomerMissing
 ```

 Learn:

 ```text id="lckm5h"
 verifyNoInteractions()
 ```

 ---

 ### 16

 ```text id="1u0n2k"
 shouldMapCustomerToCustomerResponseDTO
 ```

 ---

 ### 17

 ```text id="vg0slv"
 shouldSaveActivatedCustomer
 ```

 ---

 ### 18

 ```text id="2k1gxm"
 shouldSaveDeactivatedCustomer
 ```

 ---

 ### 19

 ```text id="ibll9n"
 shouldReturnCorrectValidationResponse
 ```

 ---

 ### 20

 ```text id="31mjlwm"
 shouldHandleEmptyOptionalReturnedByRepository
 ```

 ---

 # PHASE 3 : REPOSITORY LAYER (@DataJpaTest)

 ## What are we testing?

 ```text id="4bzq5h"
 Real Query Execution
 JPA Mapping
 Pagination
 ```

 ---

 ### 1

 ```text id="2bydga"
 shouldSaveCustomer
 ```

 ---

 ### 2

 ```text id="tzt4x6"
 shouldFindCustomerById
 ```

 ---

 ### 3

 ```text id="fmla8j"
 shouldDeleteCustomer
 ```

 ---

 ### 4

 ```text id="a22wvs"
 shouldReturnEmptyWhenCustomerNotFound
 ```

 ---

 ### 5

 ```text id="hq5dyx"
 shouldUpdateCustomer
 ```

 ---

 ### 6

 ```text id="l4gxw9"
 shouldPersistActiveStatus
 ```

 ---

 ### 7

 ```text id="vt2ym0"
 shouldPersistInactiveStatus
 ```

 ---

 ### 8

 ```text id="7e9ih0"
 shouldFindCustomerByEmailId
 ```

 ---

 ### 9

 ```text id="kmgf2f"
 shouldReturnEmptyForUnknownEmailId
 ```

 ---

 ### 10

 ```text id="pd7qhs"
 shouldFindCustomerByUsername
 ```

 ---

 ### 11

 ```text id="n2e1y8"
 shouldReturnPagedCustomers
 ```

 ---

 ### 12

 ```text id="z0mk4n"
 shouldReturnSortedCustomersAscending
 ```

 ---

 ### 13

 ```text id="f0d5ax"
 shouldReturnSortedCustomersDescending
 ```

 ---

 ### 14

 ```text id="2nyfce"
 shouldPersistDateOfBirth
 ```

 ---

 ### 15

 ```text id="dzr5yu"
 shouldPersistRole
 ```

 ---

 ### 16

 ```text id="9xpcwz"
 shouldPersistInheritanceMapping
 ```

 ---

 ### 17

 ```text id="b0l88h"
 shouldCountCustomersCorrectly
 ```

 ---

 ### 18

 ```text id="f4ejax"
 shouldCheckExistsById
 ```

 ---

 ### 19

 ```text id="jlwmqs"
 shouldReturnAllCustomers
 ```

 ---

 ### 20

 ```text id="vlj6s6"
 shouldRollbackAfterTestCompletion
 ```

 ---

 # PHASE 4 : INTEGRATION TESTS (@SpringBootTest)

 ## What are we testing?

 Entire application flow.

 ```text id="lb5m85"
 Controller
 ↓
 Service
 ↓
 Repository
 ↓
 Database
 ```

 Everything real.

 ---

 ### 1

 ```text id="o3vpyi"
 shouldCreateCustomerAndFetchCustomer
 ```

 ---

 ### 2

 ```text id="g26rk0"
 shouldActivateCustomerEndToEnd
 ```

 ---

 ### 3

 ```text id="e3n50g"
 shouldDeactivateCustomerEndToEnd
 ```

 ---

 ### 4

 ```text id="u6j5ov"
 shouldDeleteCustomerEndToEnd
 ```

 ---

 ### 5

 ```text id="31whqx"
 shouldValidateActiveCustomerEndToEnd
 ```

 ---

 ### 6

 ```text id="k7ry8v"
 shouldValidateInactiveCustomerEndToEnd
 ```

 ---

 ### 7

 ```text id="rqqxzi"
 shouldReturn404ForMissingCustomer
 ```

 ---

 ### 8

 ```text id="u5q4n4"
 shouldReturnAllCustomersEndToEnd
 ```

 ---

 ### 9

 ```text id="ekk7tm"
 shouldReturnPagedCustomersEndToEnd
 ```

 ---

 ### 10

 ```text id="zbgs2h"
 shouldReturnSortedCustomersEndToEnd
 ```

 ---

 ### 11

 ```text id="yy8rj0"
 shouldPersistCustomerInDatabase
 ```

 ---

 ### 12

 ```text id="njlwm6"
 shouldUpdateCustomerStatusInDatabase
 ```

 ---

 ### 13

 ```text id="t6ydlv"
 shouldReturnProperErrorResponse
 ```

 ---

 ### 14

 ```text id="5dhjlwm"
 shouldValidateCustomerUsingInternalApi
 ```

 ---

 ### 15

 ```text id="jdzxgq"
 shouldHandleMultipleCustomers
 ```

 ---

 ### 16

 ```text id="2vk1ea"
 shouldHandleConcurrentCustomerReads
 ```

 ---

 ### 17

 ```text id="6b6d0g"
 shouldHandleRepeatedActivationRequests
 ```

 ---

 ### 18

 ```text id="ywjfc8"
 shouldHandleRepeatedDeactivationRequests
 ```

 ---

 ### 19

 ```text id="ibp9tx"
 shouldVerifyApplicationContextLoads
 ```

 ---

 ### 20

 ```text id="x9l9ey"
 shouldExecuteCompleteCustomerLifecycle
 ```

 ```text id="c2uk3s"
 Create Customer
 ↓
 Fetch Customer
 ↓
 Activate Customer
 ↓
 Validate Customer
 ↓
 Deactivate Customer
 ↓
 Delete Customer
 ```

 This set of 80 tests is more than enough to master JUnit, Mockito, `@DataJpaTest`, `@WebMvcTest`, and `@SpringBootTest` using a single Customer module.

 * **/