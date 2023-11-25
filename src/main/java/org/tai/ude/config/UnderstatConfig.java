package org.tai.ude.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UnderstatConfig {
    private static final Logger LOGGER = LogManager.getLogger(UnderstatConfig.class);
    private final String season;
    private final String folderPath;
    private final String mainUrl;
    private final String playerUrl;

    public UnderstatConfig(String configFilePath) {
        try {
            FileInputStream propsInput = new FileInputStream(configFilePath);
            Properties prop = new Properties();
            prop.load(propsInput);

            this.season = prop.getProperty("SEASON");
            this.folderPath = prop.getProperty("FOLDER_PATH");
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

    public String getSeason() {
        return this.season;
    }

    public String getFolderPath() {
        return this.folderPath;
    }

    public String getMainUrl() {
        return this.mainUrl;
    }

    public String getPlayerUrl() {
        return this.playerUrl;
    }

    private void validateSeasonParameters() throws IllegalArgumentException {
        if (!this.season.matches("\\d{4}-\\d{2}")) {
            throw new IllegalArgumentException("season must match the following format - 2022-23");
        }
    }
}
