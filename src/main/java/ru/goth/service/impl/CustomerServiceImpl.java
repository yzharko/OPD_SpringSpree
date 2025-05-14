package ru.goth.service.impl;

import ru.goth.domain.dto.CustomerDto;
import ru.goth.repository.impl.CustomerRepositoryImpl;
import ru.goth.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepositoryImpl customerRepository;

    public CustomerServiceImpl(CustomerRepositoryImpl customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        CustomerDto newCustomerDto = new CustomerDto(customerRepository.createCustomer(
                customerDto.getId(),
                customerDto.getCityId(),
                customerDto.getName(),
                customerDto.getEmail()));
        customerDto.setId(newCustomerDto.getId());
        customerDto.setCityId(newCustomerDto.getCityId());
        customerDto.setName(newCustomerDto.getName());
        customerDto.setEmail(newCustomerDto.getEmail());
        return customerDto;
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        return customerRepository.getCustomerById(id);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        CustomerDto newCustomerDto = new CustomerDto(customerRepository.updateCustomer(
                id,
                customerDto.getCityId(),
                customerDto.getName(),
                customerDto.getEmail()));
        customerDto.setId(newCustomerDto.getId());
        customerDto.setCityId(newCustomerDto.getCityId());
        customerDto.setName(newCustomerDto.getName());
        customerDto.setEmail(newCustomerDto.getEmail());
        return customerDto;
    }

    @Override
    public boolean deleteCustomer(Long id) { return customerRepository.deleteCustomer(id); }

    @Override
    public Long existCustomer(String name) { return customerRepository.existCustomer(name); }
}
