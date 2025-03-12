package ru.goth.repository.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.goth.domain.entities.dto.CityDto;
import ru.goth.domain.entities.City;
import ru.goth.config.DBconfig;
import ru.goth.domain.mappers.CityMapper;
import ru.goth.domain.mappers.CityMapperImpl;
import ru.goth.repository.CityRepository;
import ru.goth.repository.Connect;

public class CityRepositoryImpl implements CityRepository {

    private static final Logger logger = Logger.getLogger(CityRepositoryImpl.class.getName());
    private final CityMapper cityMapper = new CityMapperImpl();

    @Override
    public CityDto createCity(Long id, String name, Time deliveryTime) {
        String createSQL = "INSERT INTO city (name, delivery_time) VALUES (?, ?)";
        DBconfig dbConnection = new DBconfig();
        Connect conn = new Connect(dbConnection);

        City city = new City(name, deliveryTime);
        city.setId(id);

        try (PreparedStatement preparedStatement = conn.getConnection().prepareStatement(createSQL)) {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setTime(2, city.getDeliveryTime());
            int rowsAffected = preparedStatement.executeUpdate();
            logger.log(Level.SEVERE, "Запись добавлена, затронуто строк: " + rowsAffected);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении записи: " + e.getMessage(), e);
        }
        return cityMapper.toCityDto(city);
    }

    @Override
    public CityDto readCityById(Long id) {
        return null;
    }

    @Override
    public List<CityDto> readAllCities() {
        String selectSQL = "SELECT * FROM city";
        DBconfig dbConnection = new DBconfig();
        Connect conn = new Connect(dbConnection);

        List<CityDto> lcd = new ArrayList<>();

        try (PreparedStatement preparedStatement = conn.getConnection().prepareStatement(selectSQL);
                 ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    City city = new City();
                    city.setId(rs.getLong("id"));
                    city.setName(rs.getString("name"));
                    city.setDeliveryTime(rs.getTime("delivery_time"));

                    lcd.add(cityMapper.toCityDto(city));
                }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении записи: " + e.getMessage(), e);
        }
        return lcd;
    }

    @Override
    public CityDto updateCity(Long id, String name, Time deliveryTime) {
        return null;
    }

    @Override
    public boolean deleteCity(Long id) {
        return false;
    }


}
