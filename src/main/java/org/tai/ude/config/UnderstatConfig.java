package org.tai.ude.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UnderstatConfig {
    private static final Logger LOGGER = LogManager.getLogger(UnderstatConfig.class);
    private final String mainSeason;
    private final int startingSeasonStart;
    private final int startingSeasonEnd;
    private final int finalSeasonEnd;
    private final String baseFilePath;
    private final String mainUrl;
    private final String playerUrl;

    public UnderstatConfig(String configFilePath) {
        try {
            FileInputStream propsInput = new FileInputStream(configFilePath);
            Properties prop = new Properties();
            prop.load(propsInput);

            this.mainSeason = prop.getProperty("MAIN_SEASON");
            this.startingSeasonStart = Integer.parseInt(prop.getProperty("STARTING_SEASON_START"));
            this.startingSeasonEnd = Integer.parseInt(prop.getProperty("STARTING_SEASON_END"));
            this.finalSeasonEnd = Integer.parseInt(prop.getProperty("FINAL_SEASON_END"));
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

    public int getStartingSeasonStart() {
        return this.startingSeasonStart;
    }

    public int getStartingSeasonEnd() {
        return convertYearTo2Digits(this.startingSeasonEnd);
    }

    public int getFinalSeasonEnd() {
        return convertYearTo2Digits(this.finalSeasonEnd);
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
        } else if (this.startingSeasonStart < 2016) {
            throw new IllegalArgumentException("Value for startingSeasonStart can not be less than 2016");
        } else if (this.startingSeasonEnd <= this.startingSeasonStart) {
            throw new IllegalArgumentException("Value for startingSeasonEnd can not be less than or equal to startingSeasonStart");
        } else if ((this.startingSeasonEnd - this.startingSeasonStart) > 1) {
            throw new IllegalArgumentException("Value for startingSeasonEnd can not be more than 1 year greater than startingSeasonStart");
        } else if (this.finalSeasonEnd <= this.startingSeasonStart) {
            throw new IllegalArgumentException("Value for finalSeasonEnd can not be less than or equal to startingSeasonStart");
        } else if (this.finalSeasonEnd < this.startingSeasonEnd) {
            throw new IllegalArgumentException("Value for finalSeasonEnd can not be less than startingSeasonEnd");
        }
    }

    private static int convertYearTo2Digits(int year) {
        return Integer.parseInt(Integer.toString(year).substring(2));
    }
}
