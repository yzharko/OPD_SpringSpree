package ru.goth.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.goth.domain.dto.CityDto;
import ru.goth.domain.dto.CustomerDto;
import ru.goth.domain.entities.Customer;
import ru.goth.config.DBconfig;
import ru.goth.domain.mappers.CustomerMapper;

import java.sql.Connection;

import ru.goth.domain.mappers.CustomerMapperImpl;
import ru.goth.repository.CustomerRepository;
import ru.goth.service.CityService;
import ru.goth.service.impl.CityServiceImpl;
import ru.goth.deliveryTimeCalculator.DeliveryTimeCalculator;

import static ru.goth.constants.RepositoryConstants.*;

public class CustomerRepositoryImpl implements CustomerRepository {

    Logger logger = Logger.getLogger(getClass().getName());
    private final CustomerMapper customerMapper = new CustomerMapperImpl();
    private final Connection connection;

    public CustomerRepositoryImpl() {
        this.connection = DBconfig.getConnection();
    }

    public CustomerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CustomerDto createCustomer(Long id, String cityName, String name, String email) {
        CityService serv = new CityServiceImpl(new CityRepositoryImpl(this.connection));
        Long cityId = serv.existCity(cityName);

        if (cityId == null) {
            Long time = DeliveryTimeCalculator.getMinutes(cityName);
            CityDto dto = new CityDto(cityName, time);
            serv.createCity(dto);
        }

        cityId = serv.existCity(cityName);

        try (PreparedStatement statement = this.connection.prepareStatement(
                     "INSERT INTO customer (city_id, name, email) " +
                             "VALUES (?, ?, ?)")) {
            Customer customer = new Customer(cityId, name, email);
            customer.setId(id);
            statement.setLong(1, customer.getCityId());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getEmail());
            int rowsAffected = statement.executeUpdate();
            logger.info(ROWS_ADDED + rowsAffected);
            return customerMapper.toCustomerDto(customer);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_CREATE, e);
            return null;
        }
    }

    @Override
    public CustomerDto createCustomer(Long id, Long cityId, String name, String email) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                     "INSERT INTO customer (city_id, name, email) " +
                             "VALUES (?, ?, ?)")) {
            Customer customer = new Customer(cityId, name, email);
            customer.setId(id);
            statement.setLong(1, customer.getCityId());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getEmail());
            int rowsAffected = statement.executeUpdate();
            logger.info(ROWS_ADDED + rowsAffected);
            return customerMapper.toCustomerDto(customer);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_CREATE, e);
            return null;
        }
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                     "SELECT id, city_id, name, email " +
                             "FROM customer " +
                             "WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Customer customer = new Customer();
            while (resultSet.next()) {
                customer.setId(resultSet.getLong("id"));
                customer.setCityId(resultSet.getLong("city_id"));
                customer.setName(resultSet.getString("name"));
                customer.setEmail(resultSet.getString("email"));
            }
            if (customer.getId() == null) {
                return null;
            }
            return customerMapper.toCustomerDto(customer);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_READ_BY_ID, e);
            return null;
        }
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        try (PreparedStatement statement = this.connection.prepareStatement(
                     "SELECT * FROM customer");
             ResultSet rs = statement.executeQuery()) {
            List<CustomerDto> lcd = new ArrayList<>();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setCityId(rs.getLong("city_id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                lcd.add(customerMapper.toCustomerDto(customer));
            }
            return lcd;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_READ_ALL, e);
            return Collections.emptyList();
        }
    }

    @Override
    public CustomerDto updateCustomer(Long id, String cityName, String name, String email) {
        CityService serv = new CityServiceImpl(new CityRepositoryImpl(this.connection));
        Long cityId = serv.existCity(cityName);

        if (cityId == null) {
            Long time = DeliveryTimeCalculator.getMinutes(cityName);
            CityDto dto = new CityDto(cityName, time);
            serv.createCity(dto);
        }

        cityId = serv.existCity(cityName);
        try (PreparedStatement statement = this.connection.prepareStatement(
                     "UPDATE customer " +
                             "SET city_id = ?, name = ?, email = ? " +
                             "WHERE id = ?")) {
            Customer customer = new Customer(cityId, name, email);
            customer.setId(id);

            statement.setLong(1, customer.getCityId());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getEmail());
            statement.setLong(4, customer.getId());
            int rowsAffected = statement.executeUpdate();
            logger.info(ROWS_UPDATED + rowsAffected);
            return customerMapper.toCustomerDto(customer);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_UPDATE, e);
            return null;
        }
    }

    @Override
    public CustomerDto updateCustomer(Long id, Long cityId, String name, String email) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                     "UPDATE customer " +
                             "SET city_id = ?, name = ?, email = ?" +
                             "WHERE id = ?")) {
            Customer customer = new Customer(cityId, name, email);
            customer.setId(id);

            statement.setLong(1, customer.getCityId());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getEmail());
            statement.setLong(4, customer.getId());
            int rowsAffected = statement.executeUpdate();
            logger.info(ROWS_UPDATED + rowsAffected);
            return customerMapper.toCustomerDto(customer);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_UPDATE, e);
            return null;
        }
    }

    @Override
    public boolean deleteCustomer(Long id) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM customer WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_DELETE, e);
        }
        return false;
    }

    @Override
    public Long existCustomer(String name) {
        Long id = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT id FROM customer WHERE name = ?")) {

            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                id = rs.getLong("id");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_EXIST, e);
        }
        return id;
    }
}
