package ru.goth.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.goth.config.DBconfig;

public class Connect {
    private static final Logger logger = Logger.getLogger(Connect.class.getName());
    Connection connection;

    public Connect(DBconfig dataSource) {
        try {
            this.connection = DBconfig.getDataSource().getConnection();
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при подключении к бд", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Ошибка при закрытие бд", e);
            }
        }
    }
}

