package org.tai.ude;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.tai.ude.writers.FileWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TestHelper {
    private static final Logger LOGGER = LogManager.getLogger(TestHelper.class);
    protected static final int VALID_STARTING_SEASON_START = 2020;
    protected static final int VALID_STARTING_SEASON_END = 21;
    protected static final int VALID_ENDING_SEASON_END = 22;
    protected static final String BASE_FILEPATH = "src/test/resources/data/";
    protected static final String JOINED_UNDERSTAT_PLAYERS_FILENAME = "understat_players_2020-22.csv";
    protected static final String JOINED_UNDERSTAT_TEAMS_FILENAME = "understat_teams_2020-22.csv";
    protected static final String FILE_WRITER_FILENAME = "test_filename.csv";
    protected static final FileWriter FILE_WRITER = new FileWriter(BASE_FILEPATH);

    protected static final int INVALID_STARTING_SEASON_START = 2016;
    protected static final int INVALID_STARTING_SEASON_END = 17;
    protected static final int INVALID_ENDING_SEASON_END = 18;

    protected static final JSONObject VALID_JSON_OBJECT_1 = new JSONObject()
            .put("test1", "value1")
            .put("test2", "value2")
            .put("test3", "value3");
    protected static final JSONObject VALID_JSON_OBJECT_2 = new JSONObject()
            .put("test1", "value4")
            .put("test2", "value5")
            .put("test3", "value6");
    protected static final JSONObject VALID_JSON_OBJECT_3 = new JSONObject()
            .put("test1", "value7")
            .put("test2", "value8")
            .put("test3", "value9");
    protected static final JSONObject VALID_JSON_OBJECT_4 = new JSONObject()
            .put("test1", "value10")
            .put("test2", "value11")
            .put("test3", "value12");

    protected static final JSONArray VALID_JSON_ARRAY = new JSONArray()
            .put(VALID_JSON_OBJECT_1)
            .put(VALID_JSON_OBJECT_2)
            .put(VALID_JSON_OBJECT_3)
            .put(VALID_JSON_OBJECT_4);

//    @After
//    public void deleteFiles() {
//        File baseFolder = new File(BASE_FILEPATH);
//
//        try {
//            for (File file: Objects.requireNonNull(baseFolder.listFiles())) {
//                if ((file.getName().equals(FILE_WRITER_FILENAME)) || (file.getName().equals(JOINED_UNDERSTAT_PLAYERS_FILENAME))
//                        || (file.getName().equals(JOINED_UNDERSTAT_TEAMS_FILENAME))) {
//                    file.delete();
//                }
//            }
//        } catch(NullPointerException nullPointerException) {
//            LOGGER.info(String.format("No files to delete in %s", BASE_FILEPATH));
//        }
//    }

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
