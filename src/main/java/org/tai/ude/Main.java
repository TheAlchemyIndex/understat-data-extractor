package org.tai.ude;

import org.tai.ude.config.UnderstatConfig;
import org.tai.ude.understat.UnderstatExtractor;
import org.tai.ude.writers.FileWriter;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        // TODO Do file path better
        UnderstatConfig config = new UnderstatConfig("src/main/resources/config.properties");
        final String season = config.getSeason();
        final String folderPath = config.getFolderPath();
        final String mainUrl = config.getMainUrl();
        final String playerUrl = config.getPlayerUrl();

        FileWriter fileWriter = new FileWriter(folderPath);

        // TODO Add to config
        ArrayList<String> targetLeagues = new ArrayList<>(Arrays.asList("EPL", "La_liga", "Bundesliga", "Serie_A", "Ligue_1"));

        for (String league : targetLeagues) {
            UnderstatExtractor understatExtractor = new UnderstatExtractor(season, mainUrl, playerUrl, fileWriter, league);
            understatExtractor.getTeamData();
            understatExtractor.getPlayerData();
        }
    }
}
