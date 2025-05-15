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

    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "test_name";
    private static final Long TEST_CITY_ID = 1L;
    private static final String TEST_CITY_NAME = "test_city_name";
    private static final String TEST_EMAIL = "test_email";
    private static final Long TEST_DELIVERY_TIME = 500L;
    private static final String TESTCONTAINER_DATA = "DB_Test";

    @Container
    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName(TESTCONTAINER_DATA)
            .withUsername(TESTCONTAINER_DATA)
            .withPassword(TESTCONTAINER_DATA);

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
        assertNull(cityRepository.existCity(TEST_CITY_NAME));

        CustomerDto customer = new CustomerDto(connection,TEST_CITY_NAME, TEST_NAME, TEST_EMAIL);

        assertNotNull(customer.getCityId());
        assertNotNull(cityRepository.existCity(TEST_CITY_NAME));
    }

    @Test
    void shouldUseExistingCity() {
        CityDto existingCity = new CityDto(TEST_CITY_NAME, TEST_DELIVERY_TIME);
        cityRepository.createCity(existingCity.getId(), existingCity.getName(), existingCity.getDeliveryTime());
        Long parisId = cityRepository.existCity(TEST_CITY_NAME);

        CustomerDto customer = new CustomerDto(connection,TEST_CITY_NAME, TEST_NAME, TEST_EMAIL);

        assertEquals(parisId, customer.getCityId());
    }

    @Test
    void constructorWithCityId_SetsFieldsCorrectly() {
        Long cityId = TEST_CITY_ID;
        String name = TEST_NAME;
        String email = TEST_EMAIL;

        CustomerDto customer = new CustomerDto(cityId, name, email);

        assertEquals(cityId, customer.getCityId());
        assertEquals(name, customer.getName());
        assertEquals(email, customer.getEmail());
    }

    @Test
    void copyConstructorTest() {
        CustomerDto original = new CustomerDto();
        original.setId(TEST_ID);
        original.setCityId(TEST_CITY_ID);
        original.setName(TEST_NAME);
        original.setEmail(TEST_EMAIL);

        CustomerDto copy = new CustomerDto(original);

        assertEquals(original.getId(), copy.getId());
        assertEquals(original.getCityId(), copy.getCityId());
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getEmail(), copy.getEmail());
    }

    @Test
    void setIdTest() {
        CustomerDto customer = new CustomerDto();
        customer.setId(TEST_ID);
            assertEquals(TEST_ID, customer.getId());
    }

    @Test
    void setNameTest() {
        CustomerDto customer = new CustomerDto();
        customer.setName(TEST_NAME);
        assertEquals(TEST_NAME, customer.getName());
    }

    @Test
    void getNameTest() {
        CustomerDto customer = new CustomerDto(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        assertEquals(TEST_NAME, customer.getName());
    }

    @Test
    void setCityIdTest() {
        CustomerDto customer = new CustomerDto();
        customer.setCityId(TEST_CITY_ID);
        assertEquals(TEST_CITY_ID, customer.getCityId());
    }

    @Test
    void getCityIdTest() {
        CustomerDto customer = new CustomerDto(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        assertEquals(TEST_CITY_ID, customer.getCityId());
    }

    @Test
    void setEmailTest() {
        CustomerDto customer = new CustomerDto();
        customer.setEmail(TEST_EMAIL);
        assertEquals(TEST_EMAIL, customer.getEmail());
    }

    @Test
    void getEmailTest() {
        CustomerDto customer = new CustomerDto(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        assertEquals(TEST_EMAIL, customer.getEmail());
    }
}
