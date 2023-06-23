package org.tai.ude.joiners;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.tai.ude.writers.FileWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UnderstatJoiner {
    private static final Logger LOGGER = LogManager.getLogger(UnderstatJoiner.class);
    private final int startingSeasonStart;
    private final int startingSeasonEnd;
    private final int endingSeasonEnd;
    private final FileWriter fileWriter;

    public UnderstatJoiner(int startingSeasonStart, int startingSeasonEnd, int endingSeasonEnd, FileWriter fileWriter) throws IllegalArgumentException {
        this.startingSeasonStart = startingSeasonStart;
        this.startingSeasonEnd = startingSeasonEnd;
        this.endingSeasonEnd = endingSeasonEnd;
        this.fileWriter = fileWriter;
    }

    public void joinPlayerData(String baseFilePath, String subFilePath) {
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
                            this.fileWriter.write(allPlayers, subFilePath);
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
                            fileWriter.write(allTeams, subFilePath);
                        }
                    }
                }
            }
        } catch(IOException ioException) {
            LOGGER.error("Error joining team understat files together: " + ioException.getMessage());
        }
    }
}
