package ru.goth.domain.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import ru.goth.domain.dto.CityDto;
import ru.goth.repository.impl.CityRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.goth.repository.CityRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class CustomerTest {

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

        Customer customer = new Customer(connection, TEST_CITY_NAME, TEST_NAME, TEST_EMAIL);

        assertNotNull(customer.getCityId());
        System.out.println(cityRepository.getAllCities());
        assertNotNull(cityRepository.existCity(TEST_CITY_NAME));
    }

    @Test
    void shouldUseExistingCity() {
        CityDto existingCity = new CityDto(TEST_CITY_NAME, TEST_DELIVERY_TIME);
        cityRepository.createCity(existingCity.getId(), existingCity.getName(), existingCity.getDeliveryTime());
        Long cityId = cityRepository.existCity(TEST_CITY_NAME);

        Customer customer = new Customer(connection, TEST_CITY_NAME, TEST_NAME, TEST_EMAIL);

        assertEquals(cityId, customer.getCityId());
    }

    @Test
    void constructorWithCityIdTest() {
        Customer customer = new Customer(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);

        assertEquals(TEST_CITY_ID, customer.getCityId());
        assertEquals(TEST_NAME, customer.getName());
        assertEquals(TEST_EMAIL, customer.getEmail());
    }

    @Test
    void copyConstructorTest() {
        Customer original = new Customer();
        original.setId(TEST_ID);
        original.setCityId(TEST_CITY_ID);
        original.setName(TEST_NAME);
        original.setEmail(TEST_EMAIL);

        Customer copy = new Customer(original);

        assertEquals(original.getId(), copy.getId());
        assertEquals(original.getCityId(), copy.getCityId());
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getEmail(), copy.getEmail());
    }

    @Test
    void setIdTest() {
        Customer customer = new Customer();
        customer.setId(TEST_ID);
        assertEquals(TEST_ID, customer.getId());
    }

    @Test
    void setNameTest() {
        Customer customer = new Customer();
        customer.setName(TEST_NAME);
        assertEquals(TEST_NAME, customer.getName());
    }

    @Test
    void getNameTest() {
        Customer customer = new Customer(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        assertEquals(TEST_NAME, customer.getName());
    }

    @Test
    void setCityIdTest() {
        Customer customer = new Customer();
        customer.setCityId(TEST_CITY_ID);
        assertEquals(TEST_CITY_ID, customer.getCityId());
    }

    @Test
    void getCityIdTest() {
        Customer customer = new Customer(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        assertEquals(TEST_CITY_ID, customer.getCityId());
    }

    @Test
    void setEmailTest() {
        Customer customer = new Customer();
        customer.setEmail(TEST_EMAIL);
        assertEquals(TEST_EMAIL, customer.getEmail());
    }

    @Test
    void getEmailTest() {
        Customer customer = new Customer(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        assertEquals(TEST_EMAIL, customer.getEmail());
    }

    @Test
    void equalsTest() {
        Customer customer1 = new Customer();
        customer1.setId(1L);

        Customer customer2 = new Customer();
        customer2.setId(1L);

        Customer customer3 = new Customer();
        customer3.setId(2L);

        Customer customer4 = customer1;

        String notACustomer = "I'm not a Customer object";

        assertTrue(customer1.equals(customer1), "Объект должен быть равен самому себе");

        assertEquals(customer1, customer2, "Симметричность не выполняется (1)");
        assertEquals(customer2, customer1, "Симметричность не выполняется (2)");

        Customer customer5 = new Customer();
        customer5.setId(1L);
        assertEquals(customer1, customer2, "Транзитивность не выполняется (1)");
        assertEquals(customer2, customer5, "Транзитивность не выполняется (2)");
        assertEquals(customer1, customer5, "Транзитивность не выполняется (3)");

        assertEquals(customer1, customer2, "Согласованность не выполняется (1)");
        assertEquals(customer1, customer2, "Согласованность не выполняется (2)");

        assertNotEquals(null, customer1, "Сравнение с null должно возвращать false");

        assertFalse(customer1.equals(notACustomer), "Сравнение с объектом другого класса должно возвращать false");

        assertNotEquals(customer1, customer3, "Объекты с разными ID не должны быть равны");

        assertEquals(customer1, customer4, "Сравнение того же объекта должно возвращать true");

        Customer customer6 = new Customer();
        Customer customer7 = new Customer();
        assertEquals(customer6, customer7, "Два объекта с null id должны быть равны");

        assertNotEquals(customer1, customer6, "Объект с id не должен быть равен объекту с null id");
        assertNotEquals(customer6, customer1, "Объект с null id не должен быть равен объекту с id");
    }

    @Test
    void hashCodeTest() {
        Customer customer1 = new Customer(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        customer1.setId(TEST_ID);

        Customer customer2 = new Customer(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        customer2.setId(TEST_ID);

        Customer customer3 = new Customer(TEST_CITY_ID, "other_name", "other@email.com");
        customer3.setId(2L);

        assertEquals(customer1, customer2);
        assertNotEquals(customer1, customer3);
        assertEquals(customer1.hashCode(), customer2.hashCode());
    }

    @Test
    void toStringTest() {
        Customer customer = new Customer(TEST_CITY_ID, TEST_NAME, TEST_EMAIL);
        customer.setId(TEST_ID);

        String expectedString = "Customer{" +
                "id=" + TEST_ID +
                ", city_id='" + TEST_CITY_ID + '\'' +
                ", name='" + TEST_NAME + '\'' +
                ", email=" + TEST_EMAIL + '}';

        assertEquals(expectedString, customer.toString());
    }
}
