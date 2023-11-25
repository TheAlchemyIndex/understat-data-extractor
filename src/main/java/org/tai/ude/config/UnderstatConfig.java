package org.tai.ude.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UnderstatConfig {
    private static final Logger LOGGER = LogManager.getLogger(UnderstatConfig.class);
    private final String mainSeason;
    private final String baseFilePath;
    private final String mainUrl;
    private final String playerUrl;

    public UnderstatConfig(String configFilePath) {
        try {
            FileInputStream propsInput = new FileInputStream(configFilePath);
            Properties prop = new Properties();
            prop.load(propsInput);

            this.mainSeason = prop.getProperty("MAIN_SEASON");
            this.baseFilePath = prop.getProperty("BASE_FILEPATH");
            this.mainUrl = prop.getProperty("MAIN_URL");
            this.playerUrl = prop.getProperty("PLAYER_URL");
            validateSeasonParameters();

            LOGGER.info("Config file successfully loaded.");
        } catch (IOException fileNotFoundException) {
            throw new RuntimeException(String.format("Error loading config file: {%s}", fileNotFoundException.getMessage()));
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new RuntimeException(String.format("Error with season parameters: {%s}", illegalArgumentException.getMessage()));
        }
    }

    public String getMainSeason() {
        return this.mainSeason;
    }

    public String getBaseFilePath() {
        return this.baseFilePath;
    }

    public String getMainUrl() {
        return String.format("%s%s", this.mainUrl, this.mainSeason.substring(0, 4));
    }

    public String getPlayerUrl() {
        return this.playerUrl;
    }

    private void validateSeasonParameters() throws IllegalArgumentException {
        if (!this.mainSeason.matches("\\d{4}-\\d{2}")) {
            throw new IllegalArgumentException("Main season must match the following format - 2022-23");
        }
    }
}
