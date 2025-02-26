package ru.goth.repository.impl;

import ru.goth.config.DBconfig;
import ru.goth.domain.dto.CityDto;
import ru.goth.domain.entities.City;
import ru.goth.domain.mappers.CityMapper;
import ru.goth.domain.mappers.CityMapperImpl;
import ru.goth.repository.CityRepository;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.goth.constants.RepositoryConstants.*;

public class CityRepositoryImpl implements CityRepository {

    Logger logger = Logger.getLogger(getClass().getName());
    private final CityMapper cityMapper = new CityMapperImpl();
    private final Connection connection;

    public CityRepositoryImpl() {
        this.connection = DBconfig.getConnection();
    }

    public CityRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CityDto createCity(Long id, String name, Long deliveryTime) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO city (name, delivery_time) " +
                        "VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            City city = new City(name, deliveryTime);
            city.setId(id);
            statement.setString(1, city.getName());
            statement.setLong(2, city.getDeliveryTime());
            int rowsAffected = statement.executeUpdate();
            return cityMapper.toCityDto(city);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_CREATE, e);
            return null;
        }
    }

    @Override
    public CityDto getCityById(Long id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "SELECT id, name, delivery_time " +
                        "FROM city " +
                        "WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            City city = new City();
            while (resultSet.next()) {
                city.setId(resultSet.getLong("id"));
                city.setName(resultSet.getString("name"));
                city.setDeliveryTime(resultSet.getLong("delivery_time"));
            }
            return cityMapper.toCityDto(city);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_READ_BY_ID, e);
            return null;
        }
    }

    @Override
    public List<CityDto> getAllCities() {
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM city");
             ResultSet rs = statement.executeQuery()) {
            List<CityDto> lcd = new ArrayList<>();
            while (rs.next()) {
                City city = new City();
                city.setId(rs.getLong("id"));
                city.setName(rs.getString("name"));
                city.setDeliveryTime(rs.getLong("delivery_time"));
                lcd.add(cityMapper.toCityDto(city));
            }
            return lcd;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_READ_ALL, e);
            return Collections.emptyList();
        }
    }

    @Override
    public CityDto updateCity(Long id, String name, Long deliveryTime) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                """
                        UPDATE city
                                                SET name = ?, delivery_time = ?
                                                WHERE id = ?
                        """)) {
            City city = new City(name, deliveryTime);
            city.setId(id);

            statement.setString(1, city.getName());
            statement.setLong(2, city.getDeliveryTime());
            statement.setLong(3, city.getId());

            int rowsAffected = statement.executeUpdate();

            return cityMapper.toCityDto(city);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_UPDATE, e);
            return null;
        }
    }

    @Override
    public boolean deleteCity(Long id) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(
                "DELETE FROM city WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, ERROR_IN_DELETE, e);
        }
        return false;
    }

    @Override
    public Long existCity(String name) {
        Long id = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(
                "SELECT id FROM city WHERE name = ?")) {
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
