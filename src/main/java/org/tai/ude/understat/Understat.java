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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.tai.ude.understat.UnderstatNameFormatter.formatName;

public class Understat {
    private static final Logger LOGGER = LogManager.getLogger(Understat.class);

    private static final String TARGET_URL = "https://understat.com/league/EPL/2022";
    private static final String TARGET_PLAYER_URL = "https://understat.com/player/";

    private static final String TEAMS_DATA_VAR = "var teamsData";
    private static final String PLAYERS_DATA_VAR = "var playersData";
    private static final String MATCHES_DATA_VAR = "var matchesData";

    private final FileWriter fileWriter;
    private final String season;

    public Understat(FileWriter fileWriter, String season) {
        this.fileWriter = fileWriter;
        this.season = season;
    }

    public void getTeamData() {
        try {
            JSONObject teamsData = getJsonObject(TARGET_URL, TEAMS_DATA_VAR);
            teamsData.keySet().forEach(keyStr ->
            {
                JSONObject teamData = teamsData.getJSONObject(keyStr);
                String teamName = formatTeamName(teamData.getString("title"));
                JSONArray teamHistory = teamData.getJSONArray("history");
                JSONArray teamMatchesDataWithTeamName = addTeamNameAndSeason(teamHistory, teamName, this.season);
                try {
                    this.fileWriter.writeDataToSeasonPath(teamMatchesDataWithTeamName, String.format("%s%s.csv", FileNames.UNDERSTAT_TEAMS_FILENAME, teamName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch(IOException ioException) {
            if (ioException instanceof UnsupportedEncodingException) {
                LOGGER.error("Error encoding hex data to Json: " + ioException.getMessage());
            } else {
                LOGGER.error("Error getting player understat data: " + ioException.getMessage());
            }
        }
    }

    public void getPlayerData() {
        try {
            JSONArray playerData = getJsonArray(TARGET_URL, PLAYERS_DATA_VAR);
            for (int i = 0; i < playerData.length(); i++) {
                int playerId = playerData.getJSONObject(i).getInt("id");
                String playerName;

                /* Temporary fix */
                if (playerId == 1245) {
                    playerName = "Emerson";
                } else if (playerId == 7430) {
                    playerName = "Emerson Royal";
                } else {
                    playerName = formatName(playerData.getJSONObject(i).getString("player_name"));
                }

                JSONArray playerMatchesData = getJsonArray(String.format("%s%s", TARGET_PLAYER_URL, playerId), MATCHES_DATA_VAR);
                JSONArray currentSeasonData = filterCurrentSeason(playerMatchesData);
                JSONArray playerMatchesDataWithName = addPlayerName(currentSeasonData, playerName);
                this.fileWriter.writeDataToSeasonPath(playerMatchesDataWithName, String.format("%s%s.csv", FileNames.UNDERSTAT_PLAYERS_FILENAME, playerName));
            }
        } catch(IOException ioException) {
            if (ioException instanceof UnsupportedEncodingException) {
                LOGGER.error("Error encoding hex data to Json: " + ioException.getMessage());
            } else {
                LOGGER.error("Error getting player understat data: " + ioException.getMessage());
            }
        }
    }

    private static String extractScriptTag(Elements scriptTags, String targetVar) {
        String scriptTagString = "";
        for (Element script : scriptTags) {
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

    private static String cleanScriptTag(String rawDataString) {
        String cleanedDataString = "";
        Pattern pattern = Pattern.compile("\\(\\'(.*?)\\'\\)");
        Matcher matcher = pattern.matcher(rawDataString);
        while (matcher.find()) {
            cleanedDataString = matcher.group(1);
        }
        return cleanedDataString;
    }

    private static JSONObject hexToJsonObject(String hexString) throws UnsupportedEncodingException {
        String jsonString = hexString.replaceAll("\\\\x", "%"); // Convert the hex string to a regular string
        jsonString = java.net.URLDecoder.decode(jsonString, "UTF-8"); // Decode the URL-encoded characters
        return new JSONObject(jsonString); // Parse the JSON string into a JSONObject
    }

    private static JSONArray hexToJsonArray(String hexString) throws UnsupportedEncodingException {
        String jsonString = hexString.replaceAll("\\\\x", "%");
        jsonString = java.net.URLDecoder.decode(jsonString, "UTF-8");
        return new JSONArray(jsonString);
    }

    private static JSONObject getJsonObject(String url, String targetVar) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements scriptTags = doc.select("script");

        String rawDataString = extractScriptTag(scriptTags, targetVar);
        String cleanedDataString = cleanScriptTag(rawDataString);
        return hexToJsonObject(cleanedDataString);
    }

    private static JSONArray getJsonArray(String url, String targetVar) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements scriptTags = doc.select("script");

        String rawDataString = extractScriptTag(scriptTags, targetVar);
        String cleanedDataString = cleanScriptTag(rawDataString);
        return hexToJsonArray(cleanedDataString);
    }

    private static JSONArray addPlayerName(JSONArray playerData, String playerName) {
        for (int i = 0; i < playerData.length(); i++) {
            JSONObject matchesData = playerData.getJSONObject(i);
            matchesData.put("name", playerName);
        }
        return playerData;
    }

    private static JSONArray addTeamNameAndSeason(JSONArray teamData, String teamName, String season) {
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
}
