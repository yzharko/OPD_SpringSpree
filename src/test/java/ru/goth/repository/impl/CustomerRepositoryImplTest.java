package ru.goth.repository.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import ru.goth.domain.dto.CityDto;
import ru.goth.domain.dto.CustomerDto;
import ru.goth.repository.CustomerRepository;
import ru.goth.repository.impl.CityRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.goth.repository.CityRepository;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

@Testcontainers
public class CustomerRepositoryImplTest {

    private static final Long TEST_ID = 1L;
    private static final String TEST_CITY_NAME = "Москва";
    private static final String TEST_NAME = "TestCustomer";
    private static final String TEST_EMAIL = "test@example.com";
    private static final Long TEST_DELIVERY_TIME = 60L;
    private static final String TESTCONTAINER_DATA = "DB_Test";

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName(TESTCONTAINER_DATA)
            .withUsername(TESTCONTAINER_DATA)
            .withPassword(TESTCONTAINER_DATA);

    private Connection connection;
    private CustomerRepository repository;
    private CityRepository cityRepository;

    @BeforeEach
    public void setUp() throws SQLException {
        postgres.start();

        connection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        try (PreparedStatement statement = connection.prepareStatement(
                """
                        CREATE TABLE city (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(50) NOT NULL,
                        	delivery_time INT
                        );
                        """)) {
            statement.execute();
        }

        try (PreparedStatement statement = connection.prepareStatement(
                """
                        CREATE TABLE customer (
                            id SERIAL PRIMARY KEY,
                            city_id INT REFERENCES city(id),
                            name VARCHAR(100) NOT NULL,
                            email VARCHAR(100) NOT NULL
                        );
                        """)) {
            statement.execute();
        }

        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM city")) {
            statement.execute();
        }
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM customer")) {
            statement.execute();
        }

        cityRepository = new CityRepositoryImpl(connection);
        repository = new CustomerRepositoryImpl(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
        postgres.stop();
    }

    @Test
    void createCustomer_WithCityName_ShouldCreateNewCityAndCustomer() {
        // Act
        CustomerDto result = repository.createCustomer(TEST_ID, TEST_CITY_NAME, TEST_NAME, TEST_EMAIL);
        System.out.println(cityRepository.getAllCities());

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(TEST_NAME, result.getName());
        assertEquals(TEST_EMAIL, result.getEmail());

        // Проверяем, что город был создан
        Long cityId = cityRepository.existCity(TEST_CITY_NAME);
        assertNotNull(cityId);
    }

    @Test
    void createCustomer_WithExistingCityId_ShouldCreateCustomer() throws SQLException {
        // Arrange
        CityDto city = new CityDto(TEST_CITY_NAME, TEST_DELIVERY_TIME);
        cityRepository.createCity(city.getId(), city.getName(), city.getDeliveryTime());
        Long cityId = cityRepository.existCity(TEST_CITY_NAME);

        // Act
        CustomerDto result = repository.createCustomer(TEST_ID, cityId, TEST_NAME, TEST_EMAIL);

        // Assert
        assertNotNull(result);
        assertEquals(cityId, result.getCityId());
        assertEquals(TEST_NAME, result.getName());
        assertEquals(TEST_EMAIL, result.getEmail());
    }

    @Test
    void getCustomerById_ShouldReturnCorrectCustomer() {
        // Arrange
        CustomerDto created = repository.createCustomer(TEST_ID, TEST_CITY_NAME, TEST_NAME, TEST_EMAIL);

        // Act
        CustomerDto retrieved = repository.getCustomerById(created.getId());

        // Assert
        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals(created.getName(), retrieved.getName());
        assertEquals(created.getEmail(), retrieved.getEmail());
    }

    @Test
    void getAllCustomers_ShouldReturnAllCustomers() {
        // Arrange
        repository.createCustomer(TEST_ID, TEST_CITY_NAME, TEST_NAME, TEST_EMAIL);
        repository.createCustomer(TEST_ID, TEST_CITY_NAME, "AnotherCustomer", "another@example.com");

        // Act
        List<CustomerDto> customers = repository.getAllCustomers();

        // Assert
        assertEquals(2, customers.size());
    }

    @Test
    void updateCustomer_WithCityName_ShouldUpdateCustomerAndCity() {
        // Arrange
        CustomerDto original = repository.createCustomer(TEST_ID, TEST_CITY_NAME, TEST_NAME, TEST_EMAIL);
        String newCityName = "Всеволожск";
        String newName = "UpdatedName";
        String newEmail = "updated@example.com";

        // Act
        CustomerDto updated = repository.updateCustomer(original.getId(), newCityName, newName, newEmail);

        // Assert
        assertNotNull(updated);
        assertEquals(original.getId(), updated.getId());
        assertEquals(newName, updated.getName());
        assertEquals(newEmail, updated.getEmail());

        // Проверяем, что новый город был создан
        Long newCityId = cityRepository.existCity(newCityName);
        assertNotNull(newCityId);
    }

    @Test
    void deleteCustomer_ShouldRemoveCustomer() {
        // Arrange
        CustomerDto customer = repository.createCustomer(TEST_ID, TEST_CITY_NAME, TEST_NAME, TEST_EMAIL);

        // Act
        boolean result = repository.deleteCustomer(customer.getId());

        // Assert
        assertTrue(result);
        assertNull(repository.getCustomerById(customer.getId()));
    }

    @Test
    void existCustomer_ShouldReturnCustomerIdIfExists() {
        // Arrange
        CustomerDto customer = repository.createCustomer(TEST_ID, TEST_CITY_NAME, TEST_NAME, TEST_EMAIL);

        // Act
        Long result = repository.existCustomer(TEST_NAME);

        // Assert
        assertEquals(customer.getId(), result);
    }

    @Test
    void existCustomer_ShouldReturnNullIfNotExists() {
        // Act
        Long result = repository.existCustomer("NonExistentName");

        // Assert
        assertNull(result);
    }

    @Test
    void createCustomer_ShouldReturnNullOnSQLException() throws SQLException {
        // Arrange
        Connection mockConnection = mock(Connection.class);
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception"));
        CustomerRepositoryImpl failingRepository = new CustomerRepositoryImpl(mockConnection);

        // Act
        CustomerDto result = failingRepository.createCustomer(1L, 1L, TEST_NAME, TEST_EMAIL);

        // Assert
        assertNull(result);
    }
}
