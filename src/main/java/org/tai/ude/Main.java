package org.tai.ude;

import org.tai.ude.config.UnderstatConfig;
import org.tai.ude.understat.UnderstatExtractor;
import org.tai.ude.writers.FileWriter;

public class Main {

    public static void main(String[] args) {
        UnderstatConfig config = new UnderstatConfig("src/main/resources/config.properties");
        final String mainSeason = config.getMainSeason();
        final String baseFilePath = config.getBaseFilePath();
        final String mainUrl = config.getMainUrl();
        final String playerUrl = config.getPlayerUrl();

        FileWriter fileWriter = new FileWriter(baseFilePath);

        UnderstatExtractor understatExtractor = new UnderstatExtractor(mainSeason, mainUrl, playerUrl, fileWriter);
        understatExtractor.getTeamData();
        understatExtractor.getPlayerData();
    }
}
