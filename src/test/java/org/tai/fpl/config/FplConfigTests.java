package org.tai.fpl.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FplConfigTests {

    private static FplConfig CONFIG;
    private static final String EXPECTED_SEASON = "2022-23";
    private static final String EXPECTED_BASE_FILEPATH = "data/";

    @Test
    public void validConfigFile() {
        CONFIG = new FplConfig("src/test/resources/config/testconfig.properties");

        assertEquals(EXPECTED_SEASON, CONFIG.getSeason());
        assertEquals(EXPECTED_BASE_FILEPATH, CONFIG.getBaseFilePath());
    }

    @Test
    public void invalidConfigFile() {
        CONFIG = new FplConfig("");

        assertNull(CONFIG.getSeason());
        assertNull(CONFIG.getBaseFilePath());
    }
}
