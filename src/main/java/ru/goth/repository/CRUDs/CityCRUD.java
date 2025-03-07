package ru.goth.repository.CRUDs;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.goth.repository.entities.City;
import ru.goth.config.DBconfig;
import ru.goth.repository.Connect;

public class CityCRUD {
    private static final Logger logger = Logger.getLogger(CityCRUD.class.getName());
    public void create(City city_) {
        String createSQL = "INSERT INTO city (name, delivery_time) VALUES (?, ?)";
        DBconfig dbConnection = new DBconfig();
        Connect conn = new Connect(dbConnection);

        try (PreparedStatement preparedStatement = conn.getConnection().prepareStatement(createSQL)) {
            preparedStatement.setString(1, city_.getName());
            preparedStatement.setTime(2, city_.getTime());
            int rowsAffected = preparedStatement.executeUpdate();
            logger.log(Level.SEVERE, "Запись добавлена, затронуто строк: " + rowsAffected);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении записи: " + e.getMessage(), e);
        }
    }
    public String readAll() {
        StringBuilder result = new StringBuilder();
        String selectSQL = "SELECT * FROM city";
        DBconfig dbConnection = new DBconfig();
        Connect conn = new Connect(dbConnection);
        try {
            try (PreparedStatement preparedStatement = conn.getConnection().prepareStatement(selectSQL);
                 ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    City city_ = new City();
                    city_.setId(rs.getLong("id"));
                    city_.setName(rs.getString("name"));
                    city_.setTime(rs.getTime("delivery_time"));

                    result.append(city_.toString()).append("\n");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении записи: " + e.getMessage(), e);
        }
        return result.toString();
    }


}
