package org.tai.ude.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnderstatConfigTest {

    private static UnderstatConfig CONFIG;
    private static final String EXPECTED_MAIN_SEASON = "2022-23";
    private static final int EXPECTED_STARTING_SEASON_START = 2019;
    private static final int EXPECTED_STARTING_SEASON_END = 20;
    private static final int EXPECTED_FINAL_SEASON_END = 23;
    private static final String EXPECTED_BASE_FILEPATH = "data/";
    private static final String EXPECTED_MAIN_URL = "https://testmainurl.com/2022";
    private static final String EXPECTED_PLAYER_URL = "https://testplayerurl.com/";

    @Test
    public void givenValidProperties_fplConfig_thenReturnValidConfig() {
        CONFIG = new UnderstatConfig("src/test/resources/config/test_config.properties");

        assertEquals(EXPECTED_MAIN_SEASON, CONFIG.getMainSeason());
        assertEquals(EXPECTED_STARTING_SEASON_START, CONFIG.getStartingSeasonStart());
        assertEquals(EXPECTED_STARTING_SEASON_END, CONFIG.getStartingSeasonEnd());
        assertEquals(EXPECTED_FINAL_SEASON_END, CONFIG.getFinalSeasonEnd());
        assertEquals(EXPECTED_BASE_FILEPATH, CONFIG.getBaseFilePath());
        assertEquals(EXPECTED_MAIN_URL, CONFIG.getMainUrl());
        assertEquals(EXPECTED_PLAYER_URL, CONFIG.getPlayerUrl());
    }

    @Test(expected = RuntimeException.class)
    public void givenNoProperties_fplConfig_thenThrowRuntimeException() {
        CONFIG = new UnderstatConfig("");
    }

    @Test(expected = RuntimeException.class)
    public void givenInvalidPropertiesSeasonFormat_fplConfig_thenThrowRuntimeException() {
        CONFIG = new UnderstatConfig("src/test/resources/config/invalid_test_config_season_format.properties");
    }

    @Test(expected = RuntimeException.class)
    public void givenInvalidPropertiesBefore2016_fplConfig_thenThrowRuntimeException() {
        CONFIG = new UnderstatConfig("src/test/resources/config/invalid_test_config_before_2016.properties");
    }

    @Test(expected = RuntimeException.class)
    public void givenInvalidPropertiesStartingSeasonEndBeforeStart_fplConfig_thenThrowRuntimeException() {
        CONFIG = new UnderstatConfig("src/test/resources/config/invalid_test_config_starting_season_end_before_start.properties");
    }

    @Test(expected = RuntimeException.class)
    public void givenInvalidPropertiesStartingSeasonEndMoreThan1Year_fplConfig_thenThrowRuntimeException() {
        CONFIG = new UnderstatConfig("src/test/resources/config/invalid_test_config_season_end_more_than_1_year.properties");
    }

    @Test(expected = RuntimeException.class)
    public void givenInvalidPropertiesFinalSeasonEndBeforeStartingSeasonStart_fplConfig_thenThrowRuntimeException() {
        CONFIG = new UnderstatConfig("src/test/resources/config/invalid_test_config_final_season_end_before_start.properties");
    }

    @Test(expected = RuntimeException.class)
    public void givenInvalidPropertiesFinalSeasonEndBeforeStartingSeasonEnd_fplConfig_thenThrowRuntimeException() {
        CONFIG = new UnderstatConfig("src/test/resources/config/invalid_test_config_final_season_end_before_start.properties");
    }
}
