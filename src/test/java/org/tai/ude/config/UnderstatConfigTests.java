package org.tai.ude.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UnderstatConfigTests {

    private static UnderstatConfig CONFIG;
    private static final String EXPECTED_SEASON = "2022-23";
    private static final String EXPECTED_BASE_FILEPATH = "data/";

    @Test
    public void validConfigFile() {
        CONFIG = new UnderstatConfig("src/test/resources/config/testconfig.properties", startingSeasonStart, startingSeasonEnd, finalSeasonEnd);

        assertEquals(EXPECTED_SEASON, CONFIG.getMainSeason());
        assertEquals(EXPECTED_BASE_FILEPATH, CONFIG.getBaseFilePath());
    }

    @Test
    public void invalidConfigFile() {
        CONFIG = new UnderstatConfig("", startingSeasonStart, startingSeasonEnd, finalSeasonEnd);

        assertNull(CONFIG.getMainSeason());
        assertNull(CONFIG.getBaseFilePath());
    }
}
