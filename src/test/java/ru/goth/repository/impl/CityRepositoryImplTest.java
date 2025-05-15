package ru.goth.repository.impl;

import ru.goth.domain.dto.CityDto;
import ru.goth.repository.CityRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Testcontainers
public class CityRepositoryImplTest {

    private static final String TEST_PARAMETER = "DB_Test";

    @Container
    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName(TEST_PARAMETER)
            .withUsername(TEST_PARAMETER)
            .withPassword(TEST_PARAMETER);

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

    private void printAllCities() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM city");
             ResultSet rs = statement.executeQuery()) {
            System.out.println("Содержимое таблицы cities:");
            while (rs.next()) {
                System.out.printf("id=%d, name=%s, delivery_time=%d%n",
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getLong("delivery_time"));
            }
        }
    }

    private void printCityById(Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id, name, delivery_time FROM city WHERE id = ?")) {
            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    System.out.printf("Город с ID %d:%n", id);
                    System.out.printf("  Название: %s%n", rs.getString("name"));
                    System.out.printf("  Время доставки: %d часов%n", rs.getLong("delivery_time"));
                } else {
                    System.out.printf("Город с ID %d не найден%n", id);
                }
            }
        }
    }

    @Test
    public void createCityTest() {
        CityDto createdCity = cityRepository.createCity(1L, "TEST_CITY", 24L);

        assertNotNull(createdCity, "Созданный город не должен быть null");
        assertEquals(1L, createdCity.getId(), "ID созданного города не совпадает");
        assertEquals("TEST_CITY", createdCity.getName(), "Название города не совпадает");
        assertEquals(24L, createdCity.getDeliveryTime(), "Время доставки не совпадает");
    }

    @Test
    public void getCityByIdTest() {
        CityDto createdCity = cityRepository.createCity(1L, "TEST_CITY", 24L);

        CityDto retrievedCity = cityRepository.getCityById(1L);

        assertNotNull(retrievedCity);
        assertEquals("TEST_CITY", retrievedCity.getName());
        assertEquals(24L, retrievedCity.getDeliveryTime());
    }

    @Test
    public void getAllCitiesTest() {
        cityRepository.createCity(1L, "TEST_CITY", 24L);
        cityRepository.createCity(2L, "TEST_CITY_2", 48L);

        List<CityDto> cities = cityRepository.getAllCities();

        assertEquals(2, cities.size());
    }

    @Test
    public void updateCityTest() {
        cityRepository.createCity(1L, "TEST_CITY", 24L);

        CityDto updatedCity = cityRepository.updateCity(1L, "NEW TEST_CITY", 12L);

        assertEquals("NEW TEST_CITY", updatedCity.getName());
        assertEquals(12L, updatedCity.getDeliveryTime());

        CityDto retrievedCity = cityRepository.getCityById(1L);
        assertEquals("NEW TEST_CITY", retrievedCity.getName());
    }

    @Test
    public void deleteCityTest() throws SQLException {
        printCityById(1L);
        cityRepository.createCity(1L, "TEST_CITY", 24L);

        printCityById(1L);
        boolean isDeleted = cityRepository.deleteCity(1L);

        printCityById(1L);
        assertTrue(isDeleted);
    }

    @Test
    public void existCityTest() {
        cityRepository.createCity(1L, "TEST_CITY", 24L);

        Long existingId = cityRepository.existCity("TEST_CITY");
        assertEquals(1L, existingId);

        Long nonExistingId = cityRepository.existCity("NON_EX");
        assertNull(nonExistingId);
    }
}
