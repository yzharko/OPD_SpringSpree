package ru.goth.repository.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import ru.goth.domain.entities.dto.CityDto;
import ru.goth.domain.entities.City;
import ru.goth.config.DBconfig;
import ru.goth.domain.mappers.CityMapper;
import ru.goth.domain.mappers.CityMapperImpl;

import java.sql.Connection;

import ru.goth.repository.CityRepository;

public class CityRepositoryImpl implements CityRepository {

    private static final Logger logger = Logger.getLogger(CityRepositoryImpl.class.getName());
    private final CityMapper cityMapper = new CityMapperImpl();
    private final DBconfig dbConnection = new DBconfig();

    @Override
    public CityDto createCity(Long id, String name, Time deliveryTime) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(
                     "INSERT INTO city (name, delivery_time) " +
                             "VALUES (?, ?)")) {
            City city = new City(name, deliveryTime);
            city.setId(id);
            statement.setString(1, city.getName());
            statement.setTime(2, city.getDeliveryTime());
            int rowsAffected = statement.executeUpdate();
            logger.info("Запись добавлена, затронуто строк: " + rowsAffected);
            return cityMapper.toCityDto(city);
        } catch (SQLException e) {
            logger.info(e.getMessage());
            return null;
        }

    }

    @Override
    public CityDto readCityById(Long id) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(
                     "SELECT id, name, delivery_time " +
                             "FROM city " +
                             "WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();


            City city = new City();
            while (resultSet.next()) {
                city.setId(resultSet.getLong("id"));
                city.setName(resultSet.getString("name"));
                city.setDeliveryTime(resultSet.getTime("delivery_time"));
            }
            return cityMapper.toCityDto(city);
        } catch (SQLException e) {
            logger.info(e.getMessage());
            return null;
        }
    }

    @Override
    public List<CityDto> readAllCities() {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(
                     "SELECT * FROM city");
             ResultSet rs = statement.executeQuery()) {
            List<CityDto> lcd = new ArrayList<>();
            while (rs.next()) {
                City city = new City();
                city.setId(rs.getLong("id"));
                city.setName(rs.getString("name"));
                city.setDeliveryTime(rs.getTime("delivery_time"));
                lcd.add(cityMapper.toCityDto(city));
            }
            return lcd;
        } catch (SQLException e) {
            logger.info(e.getMessage());
            return Collections.emptyList();
        }

    }

    @Override
    public CityDto updateCity(Long id, String name, Time deliveryTime) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "UPDATE city " +
                             "SET name = ?, delivery_time = ? " +
                             "WHERE id = ?")) {
            City city = new City(name, deliveryTime);
            city.setId(id);

            statement.setString(1, city.getName());
            statement.setTime(2, city.getDeliveryTime());
            statement.setLong(3, city.getId());
            int rowsAffected = statement.executeUpdate();
            logger.info("Запись обновлена, затронуто строк: " + rowsAffected);
            return cityMapper.toCityDto(city);
        } catch (SQLException e) {
            logger.info(e.getMessage());
            return null;
        }


    }

    @Override
    public boolean deleteCity(Long id) {
        return false;
    }


}
