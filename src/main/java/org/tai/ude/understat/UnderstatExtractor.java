package org.tai.ude.understat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.tai.ude.understat.util.HexToJsonConverter;
import org.tai.ude.understat.util.PlayerNameFormatter;
import org.tai.ude.understat.util.TeamNameFormatter;
import org.tai.ude.understat.util.WebScraper;
import org.tai.ude.util.constants.FileNames;
import org.tai.ude.writers.FileWriter;

public class UnderstatExtractor {
    private static final Logger LOGGER = LogManager.getLogger(UnderstatExtractor.class);
    private static final String TEAMS_DATA_VAR = "var teamsData";
    private static final String PLAYERS_DATA_VAR = "var playersData";
    private static final String MATCHES_DATA_VAR = "var matchesData";

    private final String season;
    private final String mainUrl;
    private final String playerUrl;
    private final FileWriter fileWriter;

    public UnderstatExtractor(String season, String mainUrl, String playerUrl, FileWriter fileWriter) {
        this.fileWriter = fileWriter;
        this.mainUrl = mainUrl;
        this.playerUrl = playerUrl;
        this.season = season;
    }

    public void getTeamData() {
        String targetElements = scrapeDataFromUrl(this.mainUrl, TEAMS_DATA_VAR);
        JSONObject teamData = HexToJsonConverter.toJsonObject(targetElements);
        teamData.keySet().forEach(teamKey -> {
            JSONObject individualTeamData = teamData.getJSONObject(teamKey);
            String teamName = TeamNameFormatter.formatName(individualTeamData.getString("title"));
            JSONArray teamHistory = individualTeamData.getJSONArray("history");
            JSONArray matchDataWithTeamName = addTeamNameAndSeasonToJsonArray(teamHistory, teamName, this.season);
            writeDataToFile(matchDataWithTeamName, String.format("%s%s.csv", FileNames.UNDERSTAT_TEAMS_FILENAME, teamName));
            LOGGER.info("Team data extraction from {} complete.", this.mainUrl);
        });
    }

    public void getPlayerData() {
        String targetElements = scrapeDataFromUrl(this.mainUrl, PLAYERS_DATA_VAR);
        JSONArray playerData = HexToJsonConverter.toJsonArray(targetElements);
        for (int i = 0; i < playerData.length(); i++) {
            JSONObject playerInfo = playerData.getJSONObject(i);
            int playerId = playerInfo.getInt("id");
            String playerName = getPlayerName(playerId, playerInfo.getString("player_name"));
            String playerMatchElements = scrapeDataFromUrl(getPlayerUrl(playerId), MATCHES_DATA_VAR);
            JSONArray playerMatchData = HexToJsonConverter.toJsonArray(playerMatchElements);
            JSONArray currentSeasonData = filterCurrentSeason(playerMatchData);
            JSONArray playerMatchDataWithName = addPlayerNameToJsonArray(currentSeasonData, playerName);
            writeDataToFile(playerMatchDataWithName, String.format("%s%s.csv", FileNames.UNDERSTAT_PLAYERS_FILENAME, playerName));
            LOGGER.info("Player data extraction from {} complete.", this.mainUrl);
        }
    }

    private String scrapeDataFromUrl(String url, String targetVar) {
        WebScraper webScraper = new WebScraper(url);
        return webScraper.getTargetElements(targetVar);
    }

    private String getPlayerUrl(int playerId) {
        return String.format("%s%s", this.playerUrl, playerId);
    }

    private String getPlayerName(int playerId, String playerName) {
        return switch (playerId) {
            case 1245 -> "Emerson";
            case 7430 -> "Emerson Royal";
            default -> PlayerNameFormatter.formatName(playerName);
        };
    }

    private JSONArray addPlayerNameToJsonArray(JSONArray playerData, String playerName) {
        for (int i = 0; i < playerData.length(); i++) {
            JSONObject matchData = playerData.getJSONObject(i);
            matchData.put("name", playerName);
        }
        return playerData;
    }

    private JSONArray addTeamNameAndSeasonToJsonArray(JSONArray teamData, String teamName, String season) {
        for (int i = 0; i < teamData.length(); i++) {
            JSONObject matchData = teamData.getJSONObject(i);
            matchData.put("team", teamName);
            matchData.put("season", season);
        }
        return teamData;
    }

    private JSONArray filterCurrentSeason(JSONArray playerData) {
        JSONArray currentSeasonData = new JSONArray();
        String currentSeasonYear = this.season.substring(0, 4);
        for (int i = 0; i < playerData.length(); i++) {
            JSONObject matchData = playerData.getJSONObject(i);
            if (matchData.getString("season").equals(currentSeasonYear)) {
                currentSeasonData.put(matchData);
            }
        }
        return currentSeasonData;
    }

    private void writeDataToFile(JSONArray data, String filename) {
        String filepath = String.format("%s/%s", this.season, filename);
        fileWriter.write(data, filepath);
    }
}
