package org.tai.fpl.writers;

import org.junit.Test;
import org.json.JSONArray;
import org.json.JSONObject;
import org.tai.fpl.TestWriterHelper;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class FileWriterTests extends TestWriterHelper {
    private static FileWriter FILE_WRITER;
    private static final String GAMEWEEK_FILENAME = "test_gameweek_filename.csv";
    private static final String SEASON_FILENAME = "test_season_filename.csv";

    private static final String EXPECTED_GAMEWEEK_FILEPATH = "src/test/resources/2022-23/test_gameweek_filename.csv";
    private static final String EXPECTED_SEASON_FILEPATH = "src/test/resources/test_season_filename.csv";

    private static final JSONObject VALID_JSON_OBJECT_1 = new JSONObject()
            .put("test1", "value1")
            .put("test2", "value2")
            .put("test3", "value3");
    private static final JSONObject VALID_JSON_OBJECT_2 = new JSONObject()
            .put("test1", "value4")
            .put("test2", "value5")
            .put("test3", "value6");

    private static final JSONArray VALID_JSON_ARRAY = new JSONArray()
            .put(VALID_JSON_OBJECT_1)
            .put(VALID_JSON_OBJECT_2);

    @Test
    public void createGameweekFile() throws IOException {
        FILE_WRITER = new FileWriter(BASE_FILEPATH, SEASON);
        FILE_WRITER.writeDataToSeasonPath(VALID_JSON_ARRAY, GAMEWEEK_FILENAME);

        File gameweekFile = new File(EXPECTED_GAMEWEEK_FILEPATH);
        assertTrue(gameweekFile.exists());
    }

    @Test
    public void createSeasonFile() throws IOException {
        FILE_WRITER = new FileWriter(BASE_FILEPATH, SEASON);
        FILE_WRITER.writeDataToBasePath(VALID_JSON_ARRAY, SEASON_FILENAME);

        File seasonFile = new File(EXPECTED_SEASON_FILEPATH);
        assertTrue(seasonFile.exists());
    }

    @Test
    public void writeGameweekDataToFile() throws IOException {
        FILE_WRITER = new FileWriter(BASE_FILEPATH, SEASON);
        FILE_WRITER.writeDataToSeasonPath(VALID_JSON_ARRAY, GAMEWEEK_FILENAME);

        assertTrue(readDataFromFile(String.format("%s%s", FULL_FILEPATH, GAMEWEEK_FILENAME)).similar(VALID_JSON_ARRAY));
    }

    @Test
    public void writeSeasonDataToFile() throws IOException {
        FILE_WRITER = new FileWriter(BASE_FILEPATH, SEASON);
        FILE_WRITER.writeDataToBasePath(VALID_JSON_ARRAY, SEASON_FILENAME);

        assertTrue(readDataFromFile(String.format("%s%s", BASE_FILEPATH, SEASON_FILENAME)).similar(VALID_JSON_ARRAY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullBaseFilepath() {
        FILE_WRITER = new FileWriter(null, SEASON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullSeason() {
        FILE_WRITER = new FileWriter(BASE_FILEPATH, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullBaseFilepathNullSeason() {
        FILE_WRITER = new FileWriter(null, null);
    }
}
