package ru.goth.repository;

import ru.goth.domain.dto.CustomerDto;

import java.util.List;

public interface CustomerRepository {

    CustomerDto createCustomer(Long id, String cityName, String name, String email);

    CustomerDto createCustomer(Long id, Long cityId, String name, String email);

    CustomerDto getCustomerById(Long id);

    List<CustomerDto> getAllCustomers();

    CustomerDto updateCustomer(Long id, String cityName, String name, String email);

    CustomerDto updateCustomer(Long id, Long cityId, String name, String email);

    boolean deleteCustomer(Long id);

    Long existCustomer(String name);
}
