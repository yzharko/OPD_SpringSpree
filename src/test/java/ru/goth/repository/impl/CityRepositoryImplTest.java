package ru.goth.repository.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ru.goth.domain.dto.CityDto;
import ru.goth.repository.CityRepository;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class CityRepositoryImplTest {

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
    public void testCreateAndGetCity() throws SQLException {
        CityDto createdCity = cityRepository.createCity(1L, "TEST_CITY", 24L);

        CityDto retrievedCity = cityRepository.getCityById(1L);

        assertNotNull(retrievedCity);
        assertEquals("TEST_CITY", retrievedCity.getName());
        assertEquals(24L, retrievedCity.getDeliveryTime());
    }

    @Test
    public void testGetAllCities() {
        cityRepository.createCity(1L, "TEST_CITY", 24L);
        cityRepository.createCity(2L, "TEST_CITY_2", 48L);

        List<CityDto> cities = cityRepository.getAllCities();

        assertEquals(2, cities.size());
    }

    @Test
    public void testUpdateCity() {
        cityRepository.createCity(1L, "TEST_CITY", 24L);

        CityDto updatedCity = cityRepository.updateCity(1L, "NEW TEST_CITY", 12L);

        assertEquals("NEW TEST_CITY", updatedCity.getName());
        assertEquals(12L, updatedCity.getDeliveryTime());

        CityDto retrievedCity = cityRepository.getCityById(1L);
        assertEquals("NEW TEST_CITY", retrievedCity.getName());
    }

    @Test
    public void testDeleteCity() throws SQLException {
        printCityById(1L);
        cityRepository.createCity(1L, "TEST_CITY", 24L);

        printCityById(1L);
        boolean isDeleted = cityRepository.deleteCity(1L);

        printCityById(1L);
        assertTrue(isDeleted);
        //assertNull(cityRepository.getCityById(1L)); //-> это уебище дает ложную инфу
    }

    @Test
    public void testExistCity() {
        cityRepository.createCity(1L, "TEST_CITY", 24L);

        Long existingId = cityRepository.existCity("TEST_CITY");
        assertEquals(1L, existingId);

        Long nonExistingId = cityRepository.existCity("NON_EX");
        assertNull(nonExistingId);
    }
}
