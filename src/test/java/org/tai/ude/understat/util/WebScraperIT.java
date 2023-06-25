package org.tai.ude.understat.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class WebScraperIT {
    private static final String MAIN_URL = "https://understat.com/league/EPL/2022";
    private static final String PLAYER_URL = "https://understat.com/player/1245";

    @Test
    public void givenValidMainUrlAndVarTeamsData_getTargetElements_thenReturnValidString() {
        WebScraper webScraper = new WebScraper(MAIN_URL);
        String elementString = webScraper.getTargetElements("var teamsData");

        assertNotNull(elementString);
        assertNotEquals("", elementString);

        JSONObject stringToJsonObject = HexToJsonConverter.toJsonObject(elementString);
        assertEquals(JSONObject.class, stringToJsonObject.getClass());
    }

    @Test
    public void givenValidMainUrlAndVarPlayersData_getTargetElements_thenReturnValidString() {
        WebScraper webScraper = new WebScraper(MAIN_URL);
        String elementString = webScraper.getTargetElements("var playersData");

        assertNotNull(elementString);
        assertNotEquals("", elementString);

        JSONArray stringToJsonArray = HexToJsonConverter.toJsonArray(elementString);
        assertEquals(JSONArray.class, stringToJsonArray.getClass());
    }

    @Test
    public void givenValidPlayerUrlAndVarMatchesData_getTargetElements_thenReturnValidString() {
        WebScraper webScraper = new WebScraper(PLAYER_URL);
        String elementString = webScraper.getTargetElements("var matchesData");

        assertNotNull(elementString);
        assertNotEquals("", elementString);

        JSONArray stringToJsonArray = HexToJsonConverter.toJsonArray(elementString);
        assertEquals(JSONArray.class, stringToJsonArray.getClass());
    }
}
