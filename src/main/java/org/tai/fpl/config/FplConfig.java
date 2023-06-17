package org.tai.fpl.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FplConfig {
    private static final Logger LOGGER = LogManager.getLogger(FplConfig.class);
    private String season;
    private String baseFilePath;

    public FplConfig(String configFilePath) {
        try {
            FileInputStream propsInput = new FileInputStream(configFilePath);
            Properties prop = new Properties();
            prop.load(propsInput);

            this.season = prop.getProperty("SEASON");
            this.baseFilePath = prop.getProperty("BASE_FILEPATH");
        } catch(IOException fileNotFoundException) {
            LOGGER.error("Error loading config file: " + fileNotFoundException.getMessage());
        }
    }

    public String getSeason() {
        return season;
    }

    public String getBaseFilePath() {
        return baseFilePath;
    }
}
