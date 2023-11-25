package org.tai.ude.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnderstatConfigTest {

    private static UnderstatConfig CONFIG;
    private static final String EXPECTED_MAIN_SEASON = "2022-23";
    private static final String EXPECTED_BASE_FILEPATH = "data/";
    private static final String EXPECTED_MAIN_URL = "https://testmainurl.com/2022";
    private static final String EXPECTED_PLAYER_URL = "https://testplayerurl.com/";

    @Test
    public void givenValidProperties_fplConfig_thenReturnValidConfig() {
        CONFIG = new UnderstatConfig("src/test/resources/config/test_config.properties");

        assertEquals(EXPECTED_MAIN_SEASON, CONFIG.getMainSeason());
        assertEquals(EXPECTED_BASE_FILEPATH, CONFIG.getBaseFilePath());
        assertEquals(EXPECTED_MAIN_URL, CONFIG.getMainUrl());
        assertEquals(EXPECTED_PLAYER_URL, CONFIG.getPlayerUrl());
    }

    @Test(expected = RuntimeException.class)
    public void givenNoProperties_fplConfig_thenThrowRuntimeException() {
        CONFIG = new UnderstatConfig("");
    }
}
