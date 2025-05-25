package ru.goth.service.impl;

import ru.goth.domain.dto.CustomerDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.goth.repository.impl.CustomerRepositoryImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepositoryImpl customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private final Long TEST_ID = 1L;
    private final String TEST_NAME = "Петя";
    private final Long TEST_CITY_ID = 5L;
    private final String TEST_EMAIL = "cock@mail.ru";

    @Test
    void createCustomerTest() {
        CustomerDto inputDto = new CustomerDto(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        inputDto.setId(TEST_ID);

        CustomerDto repoResponse = new CustomerDto(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        repoResponse.setId(TEST_ID);

        when(customerRepository.createCustomer(anyLong(), anyLong(), anyString(), anyString())).thenReturn(repoResponse);

        CustomerDto result = customerService.createCustomer(inputDto);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_NAME, result.getName());
        assertEquals(TEST_CITY_ID, result.getCityId());
        assertEquals(TEST_EMAIL, result.getEmail());

        verify(customerRepository).createCustomer(TEST_ID, TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
    }

    @Test
    void getCustomerByIdTest() {
        CustomerDto inputDto = new CustomerDto(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        inputDto.setId(TEST_ID);

        when(customerRepository.getCustomerById(anyLong())).thenReturn(inputDto);

        CustomerDto result = customerRepository.getCustomerById(TEST_ID);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        verify(customerRepository).getCustomerById(TEST_ID);
    }

    @Test
    void getAllCustomersTest() {
        CustomerDto inputDto = new CustomerDto(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        inputDto.setId(TEST_ID);

        when(customerRepository.getAllCustomers()).thenReturn(Collections.singletonList(inputDto));

        List<CustomerDto> result = customerService.getAllCustomers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TEST_ID, result.get(0).getId());
        verify(customerRepository).getAllCustomers();
    }

    @Test
    void updateCustomerTest() {
        CustomerDto inputDto = new CustomerDto(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        inputDto.setId(TEST_ID);

        CustomerDto repoResponse = new CustomerDto(5L, "Penis", "zalupa@mail.ru");
        repoResponse.setId(TEST_ID);

        when(customerRepository.updateCustomer(anyLong(), anyLong(), anyString(), anyString())).thenReturn(repoResponse);

        CustomerDto result = customerService.updateCustomer(TEST_ID, inputDto);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals("Penis", result.getName());
        assertEquals("zalupa@mail.ru", result.getEmail());
        assertEquals(5L, result.getCityId());

        verify(customerRepository).updateCustomer(TEST_ID, TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
    }

    @Test
    void deleteCustomerTest() {
        when(customerRepository.deleteCustomer(anyLong())).thenReturn(true);

        boolean result = customerService.deleteCustomer(TEST_ID);

        assertTrue(result);
        verify(customerRepository).deleteCustomer(TEST_ID);
    }

    @Test
    void existCustomerTest() {
        when(customerRepository.existCustomer(anyString())).thenReturn(TEST_ID);

        Long result = customerService.existCustomer(TEST_NAME);

        assertEquals(TEST_ID, result);
        verify(customerRepository).existCustomer(TEST_NAME);
    }
}
