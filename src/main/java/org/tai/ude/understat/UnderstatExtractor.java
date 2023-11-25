package org.tai.ude.understat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.tai.ude.understat.util.HexToJsonConverter;
import org.tai.ude.understat.util.PlayerNameFormatter;
import org.tai.ude.understat.util.TeamNameFormatter;
import org.tai.ude.understat.util.WebScraper;
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
    private final String league;

    public UnderstatExtractor(String season, String mainUrl, String playerUrl, FileWriter fileWriter, String league) {
        this.season = season;
        this.mainUrl = mainUrl;
        this.playerUrl = playerUrl;
        this.fileWriter = fileWriter;
        this.league = league;
    }

    public void getTeamData() {
        String targetElements = scrapeDataFromUrl(String.format("%s/%s/%s", this.mainUrl, this.league, this.season.substring(0, 4)), TEAMS_DATA_VAR);
        JSONObject teamData = HexToJsonConverter.toJsonObject(targetElements);
        teamData.keySet().forEach(teamKey -> {
            JSONObject individualTeamData = teamData.getJSONObject(teamKey);
            String teamName = TeamNameFormatter.formatName(individualTeamData.getString("title"));
            JSONArray teamHistory = individualTeamData.getJSONArray("history");
            JSONArray xGColumnsToSnakecase = teamXGColumnsToSnakecase(teamHistory);
            writeDataToFile(xGColumnsToSnakecase, String.format("teams/season=%s/league=%s/team=%s/team_data.csv", this.season, this.league, teamName));
            LOGGER.info("Team data extraction from {} complete.", this.mainUrl);
        });
    }

    public void getPlayerData() {
        String targetElements = scrapeDataFromUrl(String.format("%s/%s/%s", this.mainUrl, this.league, this.season.substring(0, 4)), PLAYERS_DATA_VAR);
        JSONArray playerData = HexToJsonConverter.toJsonArray(targetElements);
        for (int i = 0; i < playerData.length(); i++) {
            JSONObject playerInfo = playerData.getJSONObject(i);
            int playerId = playerInfo.getInt("id");
            String playerName = getPlayerName(playerId, playerInfo.getString("player_name"));
            String playerMatchElements = scrapeDataFromUrl(getPlayerUrl(playerId), MATCHES_DATA_VAR);
            JSONArray playerMatchData = HexToJsonConverter.toJsonArray(playerMatchElements);
            JSONArray currentSeasonData = filterCurrentSeason(playerMatchData);
            JSONArray playerMatchDataWithName = addPlayerNameToJsonArray(currentSeasonData, playerName);
            JSONArray playerXGColumnsToSnakecase = playerXGColumnsToSnakecase(playerMatchDataWithName);
            writeDataToFile(playerXGColumnsToSnakecase, String.format("players/season=%s/league=%s/%s.csv", this.season, this.league, playerName));
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

    private JSONArray addPlayerNameToJsonArray(JSONArray jsonArray, String playerName) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject matchData = jsonArray.getJSONObject(i);
            matchData.put("name", playerName);
        }
        return jsonArray;
    }

    private JSONArray addTeamNameAndSeasonToJsonArray(JSONArray jsonArray, String teamName, String season) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject matchData = jsonArray.getJSONObject(i);
            matchData.put("team", teamName);
            matchData.put("season", season);

            String dateValue = matchData.getString("date");
            String teamValue = matchData.getString("team");

            // Temporary fix for incorrect data in 2016-17 season
            switch (dateValue) {
                case "2017-01-04 00:00:00":
                    if (teamValue.equals("Crystal Palace") || teamValue.equals("Stoke") || teamValue.equals("Swansea") || teamValue.equals("Watford")) {
                        matchData.put("date", "2017-01-03 00:00:00");
                    }
                    break;

                case "2017-01-05 00:00:00":
                    if (teamValue.equals("Chelsea") || teamValue.equals("Spurs")) {
                        matchData.put("date", "2017-01-04 00:00:00");
                    }
                    break;
            }
        }
        return jsonArray;
    }

    private JSONArray teamXGColumnsToSnakecase(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject matchData = jsonArray.getJSONObject(i);
            Double npxG = matchData.getDouble("npxG");
            Double xG = matchData.getDouble("xG");
            Double npxGD = matchData.getDouble("npxGD");
            Double npxGA = matchData.getDouble("npxGA");
            Double xGA = matchData.getDouble("xGA");

            matchData.remove("npxG");
            matchData.remove("xG");
            matchData.remove("npxGD");
            matchData.remove("npxGA");
            matchData.remove("xGA");

            matchData.put("npx_g", npxG);
            matchData.put("x_g", xG);
            matchData.put("npx_g_d", npxGD);
            matchData.put("npx_g_a", npxGA);
            matchData.put("x_g_a", xGA);
        }
        return jsonArray;
    }

    private JSONArray playerXGColumnsToSnakecase(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject matchData = jsonArray.getJSONObject(i);
            Double npxG = matchData.getDouble("npxG");
            Double xGChain = matchData.getDouble("xGChain");
            Double xA = matchData.getDouble("xA");
            Double xG = matchData.getDouble("xG");
            Double xGBuildup = matchData.getDouble("xGBuildup");

            matchData.remove("npxG");
            matchData.remove("xGChain");
            matchData.remove("xA");
            matchData.remove("xG");
            matchData.remove("xGBuildup");

            matchData.put("npx_g", npxG);
            matchData.put("x_g_chain", xGChain);
            matchData.put("x_a", xA);
            matchData.put("x_g", xG);
            matchData.put("x_g_buildup", xGBuildup);
        }
        return jsonArray;
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
        fileWriter.write(data, filename);
    }
}
