package ru.goth.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBconfig {
    private static HikariDataSource dataSource;

    static {
        ConfigLoader configLoader = new ConfigLoader();
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(configLoader.getDriver());
        config.setJdbcUrl(configLoader.getUrl());
        config.setUsername(configLoader.getUsername());
        config.setPassword(configLoader.getPassword());

        dataSource = new HikariDataSource(config);
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
