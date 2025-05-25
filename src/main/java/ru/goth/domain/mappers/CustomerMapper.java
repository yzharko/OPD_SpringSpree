package ru.goth.domain.mappers;

import org.mapstruct.Mapper;
import ru.goth.domain.dto.CustomerDto;
import ru.goth.domain.entities.Customer;

@Mapper
public interface CustomerMapper {

    CustomerDto toCustomerDto(Customer customer);

    Customer toCustomer(CustomerDto customerDto);
}
