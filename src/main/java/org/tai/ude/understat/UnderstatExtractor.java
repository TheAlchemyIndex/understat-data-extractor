package org.tai.ude.understat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tai.ude.util.constants.FileNames;
import org.tai.ude.writers.FileWriter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.tai.ude.understat.util.PlayerNameFormatter.formatName;

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
        try {
            JSONObject teamsData = getJsonObject(this.mainUrl, TEAMS_DATA_VAR);
            teamsData.keySet().forEach(keyStr ->
            {
                JSONObject individualTeamData = teamsData.getJSONObject(keyStr);
                String teamName = formatTeamName(individualTeamData.getString("title"));
                JSONArray teamHistory = individualTeamData.getJSONArray("history");
                JSONArray matchDataWithTeamName = addTeamNameAndSeason(teamHistory, teamName, this.season);
                writeDataToFile(matchDataWithTeamName, String.format("%s%s.csv", FileNames.UNDERSTAT_TEAMS_FILENAME, teamName));
            });
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error encoding hex data to Json: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("Error getting team understat data: " + e.getMessage());
        }
    }

    public void getPlayerData() {
        try {
            JSONArray playerData = getJsonArray(this.mainUrl, PLAYERS_DATA_VAR);
            for (int i = 0; i < playerData.length(); i++) {
                int playerId = playerData.getJSONObject(i).getInt("id");
                String playerName = getPlayerName(playerId, playerData.getJSONObject(i).getString("player_name"));
                JSONArray playerMatchData = getJsonArray(String.format("%s%s", this.playerUrl, playerId), MATCHES_DATA_VAR);
                JSONArray currentSeasonData = filterCurrentSeason(playerMatchData);
                JSONArray playerMatchDataWithName = addPlayerName(currentSeasonData, playerName);
                writeDataToFile(playerMatchDataWithName, String.format("%s%s.csv", FileNames.UNDERSTAT_PLAYERS_FILENAME, playerName));
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error encoding hex data to Json: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("Error getting player understat data: " + e.getMessage());
        }
    }

    private String getPlayerName(int playerId, String playerName) {
        return switch (playerId) {
            case 1245 -> "Emerson";
            case 7430 -> "Emerson Royal";
            default -> formatName(playerName);
        };
    }

    private JSONObject getJsonObject(String url, String targetVar) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements scriptTags = doc.select("script");

        String rawDataString = extractScriptElements(scriptTags, targetVar);
        String cleanedDataString = cleanScriptElement(rawDataString);
        return hexToJsonObject(cleanedDataString);
    }

    private JSONArray getJsonArray(String url, String targetVar) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements scriptTags = doc.select("script");

        String rawDataString = extractScriptElements(scriptTags, targetVar);
        String cleanedDataString = cleanScriptElement(rawDataString);
        return hexToJsonArray(cleanedDataString);
    }

    private String extractScriptElements(Elements scriptElements, String targetVar) {
        String scriptTagString = "";
        for (Element script : scriptElements) {
            String scriptHtml = script.html();
            Pattern pattern = Pattern.compile("/\\*(.*?)\\*/", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(scriptHtml);
            scriptHtml = matcher.replaceAll("");
            if (scriptHtml.contains(targetVar)) {
                scriptTagString = scriptHtml;
            }
        }
        return scriptTagString;
    }

    private String cleanScriptElement(String rawDataString) {
        String cleanedDataString = "";
        Pattern pattern = Pattern.compile("\\('(.*?)'\\)");
        Matcher matcher = pattern.matcher(rawDataString);
        if (matcher.find()) {
            cleanedDataString = matcher.group(1);
        }
        return cleanedDataString;
    }

    private JSONObject hexToJsonObject(String hexString) {
        String jsonString = hexString.replaceAll("\\\\x", "%");
        jsonString = java.net.URLDecoder.decode(jsonString, StandardCharsets.UTF_8);
        return new JSONObject(jsonString);
    }

    private JSONArray hexToJsonArray(String hexString) {
        String jsonString = hexString.replaceAll("\\\\x", "%");
        jsonString = java.net.URLDecoder.decode(jsonString, StandardCharsets.UTF_8);
        return new JSONArray(jsonString);
    }

    private JSONArray addPlayerName(JSONArray playerData, String playerName) {
        for (int i = 0; i < playerData.length(); i++) {
            JSONObject matchesData = playerData.getJSONObject(i);
            matchesData.put("name", playerName);
        }
        return playerData;
    }

    private JSONArray addTeamNameAndSeason(JSONArray teamData, String teamName, String season) {
        for (int i = 0; i < teamData.length(); i++) {
            JSONObject matchesData = teamData.getJSONObject(i);
            matchesData.put("team", teamName);
            matchesData.put("season", season);
        }
        return teamData;
    }

    private JSONArray filterCurrentSeason(JSONArray playerData) {
        JSONArray currentSeasonData = new JSONArray();
        for (int i = 0; i < playerData.length(); i++) {
            JSONObject matchesData = playerData.getJSONObject(i);
            if (matchesData.get("season").equals(this.season.substring(0, 4))) {
                currentSeasonData.put(matchesData);
            }
        }
        return currentSeasonData;
    }

    private String formatTeamName(String teamName) {
        return switch (teamName) {
            case "Cardiff" -> "Cardiff City";
            case "Manchester City" -> "Man City";
            case "Manchester United" -> "Man Utd";
            case "Newcastle United" -> "Newcastle";
            case "Nottingham Forest" -> "Nott'm Forest";
            case "Sheffield United" -> "Sheffield Utd";
            case "Tottenham" -> "Spurs";
            case "West Bromwich Albion" -> "West Brom";
            case "Wolverhampton Wanderers" -> "Wolves";
            default -> teamName;
        };
    }

    private void writeDataToFile(JSONArray data, String filename) {
        String filepath = String.format("%s/%s", this.season, filename);
        fileWriter.write(data, filepath);
    }
}
