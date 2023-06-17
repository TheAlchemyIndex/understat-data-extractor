package org.tai.fpl;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TestWriterHelper {
    private static final Logger LOGGER = LogManager.getLogger(TestWriterHelper.class);
    protected static final String BASE_FILEPATH = "src/test/resources/";
    protected static final String SEASON = "2022-23";
    protected static final String FULL_FILEPATH = String.format("%s%s/", BASE_FILEPATH, SEASON);

    @After
    public void deleteFiles() {
        File seasonDirectory = new File(String.format("%s%s", BASE_FILEPATH, SEASON));
        File gwDirectory = new File(String.format("%s%s/%s", BASE_FILEPATH, SEASON, "gws"));
        File gameweekJoinerDirectory = new File(String.format("%s%s/%s", BASE_FILEPATH, "gameweekJoiner", SEASON));
        File seasonJoinerDirectory = new File(String.format("%s/%s", BASE_FILEPATH, "seasonJoiner"));
        File baseDirectory = new File(BASE_FILEPATH);

        try {
            for (File file: Objects.requireNonNull(seasonDirectory.listFiles())) {
                file.delete();
            }
            seasonDirectory.delete();
        } catch(NullPointerException nullPointerException) {
            LOGGER.info(String.format("No files to delete in %s", seasonDirectory));
        }

        try {
            for (File file: Objects.requireNonNull(gwDirectory.listFiles())) {
                file.delete();
            }
            gwDirectory.delete();
        } catch(NullPointerException nullPointerException) {
            LOGGER.info(String.format("No files to delete in %s", gwDirectory));
        }

        try {
            for (File file: Objects.requireNonNull(gameweekJoinerDirectory.listFiles())) {
                file.delete();
            }
            gameweekJoinerDirectory.delete();
        } catch(NullPointerException nullPointerException) {
            LOGGER.info(String.format("No files to delete in %s", gameweekJoinerDirectory));
        }

        try {
            for (File file: Objects.requireNonNull(seasonJoinerDirectory.listFiles())) {
                file.delete();
            }
            seasonJoinerDirectory.delete();
        } catch(NullPointerException nullPointerException) {
            LOGGER.info(String.format("No files to delete in %s", seasonJoinerDirectory));
        }

        try {
            for (File file: Objects.requireNonNull(baseDirectory.listFiles())) {
                file.delete();
            }
            baseDirectory.delete();
        } catch(NullPointerException nullPointerException) {
            LOGGER.info(String.format("No files to delete in %s", baseDirectory));
        }
    }

    protected JSONArray readDataFromFile(String filepath) {
        CsvMapper csvMapper = new CsvMapper();
        ObjectMapper objectMapper = new ObjectMapper();
        JSONArray allRows = new JSONArray();

        try {
            List<Map<String, String>> rows;
            try (MappingIterator<Map<String, String>> mappingIterator = csvMapper
                    .readerWithSchemaFor(Map.class)
                    .with(CsvSchema.emptySchema().withHeader())
                    .readValues(new File(filepath))) {
                rows = mappingIterator.readAll();
            }

            for (Map<String, String> row : rows) {
                String jsonString = objectMapper.writeValueAsString(row);
                allRows.put(new JSONObject(jsonString));
            }
        } catch(IOException ioException) {
            LOGGER.error("Error reading data from file: " + ioException.getMessage());
        }
        return allRows;
    }
}
