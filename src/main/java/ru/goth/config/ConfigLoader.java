package ru.goth.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigLoader {

    Logger logger = Logger.getLogger(getClass().getName());
    private final Properties properties = new Properties();
    private static final String pathToProperties = "/application.properties";
    private static final String ERROR_fileNotFound = "Файл application.properties не найден";
    private static final String ERROR_troublesWithFileLoad = "Ошибка при загрузке application.properties";

    public ConfigLoader() {
        try (InputStream input = getClass().getResourceAsStream(pathToProperties)) {
            if (input == null) {
                logger.log(Level.SEVERE, ERROR_fileNotFound);
                return;
            }
            properties.load(input);
        } catch (IOException e) {
            logger.log(Level.SEVERE, ERROR_troublesWithFileLoad, e);
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

