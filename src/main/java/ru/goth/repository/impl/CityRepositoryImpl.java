package ru.goth.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.goth.domain.dto.CityDto;
import ru.goth.domain.entities.City;
import ru.goth.config.DBconfig;
import ru.goth.domain.mappers.CityMapper;
import ru.goth.domain.mappers.CityMapperImpl;

import java.sql.Connection;

import ru.goth.repository.CityRepository;

import static ru.goth.constants.RepositoryConstants.ERROR_IN_CREATE;
import static ru.goth.constants.RepositoryConstants.ERROR_IN_DELETE;
import static ru.goth.constants.RepositoryConstants.ERROR_IN_UPDATE;
import static ru.goth.constants.RepositoryConstants.ERROR_IN_READ_BY_ID;
import static ru.goth.constants.RepositoryConstants.ERROR_IN_READ_ALL;
import static ru.goth.constants.RepositoryConstants.ROWS_UPDATED;
import static ru.goth.constants.RepositoryConstants.ROWS_ADDED;

public class CityRepositoryImpl implements CityRepository {

    Logger logger = Logger.getLogger(getClass().getName());
    private final CityMapper cityMapper = new CityMapperImpl();

    @Override
    public CityDto createCity(Long id, String name, Time deliveryTime) {
        try (Connection con = DBconfig.getConnection();
             PreparedStatement statement = con.prepareStatement(
                     "INSERT INTO city (name, delivery_time) " +
                             "VALUES (?, ?)")) {
            City city = new City(name, deliveryTime);
            city.setId(id);
            statement.setString(1, city.getName());
            statement.setTime(2, city.getDeliveryTime());
            int rowsAffected = statement.executeUpdate();
            logger.info(ROWS_ADDED + rowsAffected);
            return cityMapper.toCityDto(city);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_CREATE, e);
            return null;
        }
    }

    @Override
    public CityDto getCityById(Long id) {
        try (Connection con = DBconfig.getConnection();
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
            logger.log(Level.SEVERE, ERROR_IN_READ_BY_ID, e);
            return null;
        }
    }

    @Override
    public List<CityDto> getAllCities() {
        try (Connection con = DBconfig.getConnection();
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
            logger.log(Level.SEVERE, ERROR_IN_READ_ALL, e);
            return Collections.emptyList();
        }
    }

    @Override
    public CityDto updateCity(Long id, String name, Time deliveryTime) {
        try (Connection conn = DBconfig.getConnection();
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
            logger.info(ROWS_UPDATED + rowsAffected);
            return cityMapper.toCityDto(city);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_UPDATE, e);
            return null;
        }
    }

    @Override
    public boolean deleteCity(Long id) {
        try (Connection conn = DBconfig.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM customer WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_DELETE, e);
        }
        return false;
    }
}
