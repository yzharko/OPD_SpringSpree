package ru.goth.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigLoader {
    private static final Logger logger = Logger.getLogger(ConfigLoader.class.getName());
    private Properties properties = new Properties();

    public ConfigLoader() {
        try (InputStream input = getClass().getResourceAsStream("/application.properties")) {
            if (input == null) {
                logger.log(Level.SEVERE, "Файл application.properties не найден");
                return;
            }
            properties.load(input);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при загрузке application.properties", e);
        }
    }
    public String getDriver() {
        return properties.getProperty("db.driver");
    }

    public String getUrl() {
        return properties.getProperty("db.url");
    }

    public String getUsername() {
        return properties.getProperty("db.username");
    }

    public String getPassword() {
        return properties.getProperty("db.password");
    }
}

