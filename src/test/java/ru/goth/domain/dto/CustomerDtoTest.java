package ru.goth.domain.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import ru.goth.repository.impl.CityRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.goth.repository.CityRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class CustomerDtoTest {

    @Container
    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName("DB_Test")
            .withUsername("DB_Test")
            .withPassword("DB_Test");

    private Connection connection;
    private CityRepository cityRepository;

    @BeforeEach
    public void setUp() throws Exception {
        postgreSQLContainer.start();

        connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
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

        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM city")) {
            statement.execute();
        }

        cityRepository = new CityRepositoryImpl(connection);
    }

    @AfterEach
    public void tearDown() throws Exception {
        connection.close();
        postgreSQLContainer.stop();
    }

    @Test
    void shouldCreateCustomerWithNewCity() {
        assertNull(cityRepository.existCity("Berlin"));

        CustomerDto customer = new CustomerDto(connection,"Berlin", "Max", "max@test.com");

        assertNotNull(customer.getCityId());
        assertNotNull(cityRepository.existCity("Berlin"));
    }

    @Test
    void shouldUseExistingCity() {
        CityDto existingCity = new CityDto("Paris", 45L);
        cityRepository.createCity(existingCity.getId(), existingCity.getName(), existingCity.getDeliveryTime());
        Long parisId = cityRepository.existCity("Paris");

        CustomerDto customer = new CustomerDto(connection,"Paris", "Alice", "alice@test.com");

        assertEquals(parisId, customer.getCityId());
    }

    @Test
    void constructorWithCityId_SetsFieldsCorrectly() {
        Long cityId = 1L;
        String name = "John Doe";
        String email = "john@example.com";

        CustomerDto customer = new CustomerDto(cityId, name, email);

        assertEquals(cityId, customer.getCityId());
        assertEquals(name, customer.getName());
        assertEquals(email, customer.getEmail());
    }

    @Test
    void copyConstructor_CopiesAllFields() {
        // Given
        CustomerDto original = new CustomerDto();
        original.setId(1L);
        original.setCityId(10L);
        original.setName("Eve");
        original.setEmail("eve@example.com");

        CustomerDto copy = new CustomerDto(original);

        assertEquals(original.getId(), copy.getId());
        assertEquals(original.getCityId(), copy.getCityId());
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getEmail(), copy.getEmail());
    }

    @Test
    void gettersAndSetters_WorkCorrectly() {
        CustomerDto customer = new CustomerDto();

        customer.setId(5L);
        customer.setCityId(20L);
        customer.setName("Test");
        customer.setEmail("test@example.com");

        assertEquals(5L, customer.getId());
        assertEquals(20L, customer.getCityId());
        assertEquals("Test", customer.getName());
        assertEquals("test@example.com", customer.getEmail());
    }
}
