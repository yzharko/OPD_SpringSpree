package ru.goth.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DBconfig {

    private final static HikariDataSource dataSource;
    private static final Logger log = Logger.getLogger(DBconfig.class.getName());
    private static final String ERROR_connectWithBD = "Ошибка при соединении с бд";
    private static final String ERROR_tryingToCreateAnInstance = "Utility class";

    private DBconfig() {
        throw new IllegalStateException(ERROR_tryingToCreateAnInstance);
    }

    static {
        ConfigLoader configLoader = new ConfigLoader();
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(configLoader.getDriver());
        config.setJdbcUrl(configLoader.getUrl());
        config.setUsername(configLoader.getUsername());
        config.setPassword(configLoader.getPassword());

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.log(Level.SEVERE, ERROR_connectWithBD, e);
            e.printStackTrace();
        }
        return null;
    }
}
