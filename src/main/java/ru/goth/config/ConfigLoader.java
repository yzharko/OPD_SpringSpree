package ru.goth.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigLoader {

    Logger logger = Logger.getLogger(getClass().getName());
    private final Properties properties = new Properties();
    private static final InputStream inputConst = null;

    public ConfigLoader() {
        try (InputStream input = getClass().getResourceAsStream("/application.properties")) {
            if (input == inputConst) {
                logger.log(Level.SEVERE, "Файл application.properties не найден");
                return;
            }
            properties.load(input);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при загрузке application.properties", e);
            e.printStackTrace();
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

