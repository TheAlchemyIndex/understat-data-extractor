package org.tai.fpl.joiners;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.tai.fpl.writers.FileWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UnderstatJoiner {
    private static final Logger LOGGER = LogManager.getLogger(UnderstatJoiner.class);
    private final int startingSeasonStart;
    private final int startingSeasonEnd;
    private final int endingSeasonEnd;

    public UnderstatJoiner(int startingSeasonStart, int startingSeasonEnd, int endingSeasonEnd) throws IllegalArgumentException {
        validateSeasonParameters(startingSeasonStart, startingSeasonEnd, endingSeasonEnd);
        this.startingSeasonStart = startingSeasonStart;
        this.startingSeasonEnd = convertYearTo2Digits(startingSeasonEnd);
        this.endingSeasonEnd = convertYearTo2Digits(endingSeasonEnd);
    }

    public void joinPlayerData(FileWriter fileWriter, String baseFilePath, String subFilePath) {
        CsvMapper csvMapper = new CsvMapper();
        ObjectMapper objectMapper = new ObjectMapper();
        JSONArray allPlayers = new JSONArray();

        try {
            for (int i = this.startingSeasonStart, j = this.startingSeasonEnd; j <= this.endingSeasonEnd; i++, j++) {
                File folder = new File(String.format("%s%s-%s/understat/players/", baseFilePath, i, j));
                if (folder.isDirectory()) {
                    File[] files = folder.listFiles();
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".csv")) {
                            List<Map<String, String>> rows;
                            try (MappingIterator<Map<String, String>> mappingIterator = csvMapper
                                    .readerWithSchemaFor(Map.class)
                                    .with(CsvSchema.emptySchema().withHeader())
                                    .readValues(new File(file.getAbsolutePath()))) {
                                rows = mappingIterator.readAll();
                            }

                            for (Map<String, String> row : rows) {
                                String jsonString = objectMapper.writeValueAsString(row);
                                allPlayers.put(new JSONObject(jsonString));
                            }
                            try {
                                fileWriter.writeDataToBasePath(allPlayers, subFilePath);
                            } catch (IOException ioException) {
                                LOGGER.error("Error writing player understat data to file: " + ioException.getMessage());
                            }
                        }
                    }
                }
            }
        } catch(IOException ioException) {
            LOGGER.error("Error joining player understat files together: " + ioException.getMessage());
        }
    }

    public void joinTeamData(FileWriter fileWriter, String baseFilePath, String subFilePath) {
        CsvMapper csvMapper = new CsvMapper();
        ObjectMapper objectMapper = new ObjectMapper();
        JSONArray allTeams = new JSONArray();

        try {
            for (int i = this.startingSeasonStart, j = this.startingSeasonEnd; j <= this.endingSeasonEnd; i++, j++) {
                File folder = new File(String.format("%s%s-%s/understat/teams/", baseFilePath, i, j));
                if (folder.isDirectory()) {
                    File[] files = folder.listFiles();
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".csv")) {
                            List<Map<String, String>> rows;
                            try (MappingIterator<Map<String, String>> mappingIterator = csvMapper
                                    .readerWithSchemaFor(Map.class)
                                    .with(CsvSchema.emptySchema().withHeader())
                                    .readValues(new File(file.getAbsolutePath()))) {
                                rows = mappingIterator.readAll();
                            }

                            for (Map<String, String> row : rows) {
                                String jsonString = objectMapper.writeValueAsString(row);
                                allTeams.put(new JSONObject(jsonString));
                            }
                            try {
                                fileWriter.writeDataToBasePath(allTeams, subFilePath);
                            } catch (IOException ioException) {
                                LOGGER.error("Error writing team understat data to file: " + ioException.getMessage());
                            }
                        }
                    }
                }
            }
        } catch(IOException ioException) {
            LOGGER.error("Error joining team understat files together: " + ioException.getMessage());
        }
    }

    private void validateSeasonParameters(int startingSeasonStart, int startingSeasonEnd, int endingSeasonEnd) throws IllegalArgumentException {
        if (startingSeasonStart < 2016) {
            throw new IllegalArgumentException("Value for startingSeasonStart can not be less than 2016.");
        } else if (startingSeasonEnd <= startingSeasonStart) {
            throw new IllegalArgumentException("Value for startingSeasonEnd can not be less than or equal to startingSeasonStart.");
        } else if ((startingSeasonEnd - startingSeasonStart) > 1) {
            throw new IllegalArgumentException("Value for startingSeasonEnd can not be more than 1 year greater than startingSeasonStart.");
        } else if (endingSeasonEnd <= startingSeasonStart) {
            throw new IllegalArgumentException("Value for endingSeasonEnd can not be less than or equal to startingSeasonStart.");
        }
    }

    private int convertYearTo2Digits(int year) {
        return Integer.parseInt(Integer.toString(year).substring(2));
    }
}
