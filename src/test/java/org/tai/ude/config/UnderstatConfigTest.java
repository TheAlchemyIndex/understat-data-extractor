package org.tai.ude.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnderstatConfigTest {

    private static UnderstatConfig CONFIG;
    private static final String EXPECTED_SEASON = "2022-23";
    private static final String EXPECTED_BUCKET = "data/";
    private static final String EXPECTED_MAIN_URL = "https://testmainurl.com/";
    private static final String EXPECTED_PLAYER_URL = "https://testplayerurl.com/";

    @Test
    public void givenValidProperties_fplConfig_thenReturnValidConfig() {
        CONFIG = new UnderstatConfig("src/test/resources/config/test_config.properties");

        assertEquals(EXPECTED_SEASON, CONFIG.getSeason());
        assertEquals(EXPECTED_BUCKET, CONFIG.getBucket());
        assertEquals(EXPECTED_MAIN_URL, CONFIG.getMainUrl());
        assertEquals(EXPECTED_PLAYER_URL, CONFIG.getPlayerUrl());
    }

    @Test(expected = RuntimeException.class)
    public void givenNoProperties_fplConfig_thenThrowRuntimeException() {
        CONFIG = new UnderstatConfig("");
    }
}
