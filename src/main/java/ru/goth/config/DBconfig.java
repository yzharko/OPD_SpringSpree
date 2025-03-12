package ru.goth.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DBconfig {

    private final static HikariDataSource dataSource;
    private static final Logger log = LoggerFactory.getLogger(DBconfig.class);

    static {
        ConfigLoader configLoader = new ConfigLoader();
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(configLoader.getDriver());
        config.setJdbcUrl(configLoader.getUrl());
        config.setUsername(configLoader.getUsername());
        config.setPassword(configLoader.getPassword());

        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
