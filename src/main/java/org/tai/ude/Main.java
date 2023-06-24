package org.tai.ude;

import org.tai.ude.config.UnderstatConfig;
import org.tai.ude.joiners.UnderstatJoiner;
import org.tai.ude.understat.UnderstatExtractor;
import org.tai.ude.writers.FileWriter;

public class Main {

    public static void main(String[] args) {
        UnderstatConfig config = new UnderstatConfig("src/main/resources/config.properties");
        final String mainSeason = config.getMainSeason();
        final int startingSeasonStart = config.getStartingSeasonStart();
        final int startingSeasonEnd = config.getStartingSeasonEnd();
        final int finalSeasonEnd = config.getFinalSeasonEnd();
        final String baseFilePath = config.getBaseFilePath();
        final String mainUrl = config.getMainUrl();
        final String playerUrl = config.getPlayerUrl();

        FileWriter fileWriter = new FileWriter(baseFilePath);

        UnderstatExtractor understatExtractor = new UnderstatExtractor(mainSeason, mainUrl, playerUrl, fileWriter);
        understatExtractor.getTeamData();
        understatExtractor.getPlayerData();

        UnderstatJoiner understatJoiner = new UnderstatJoiner(startingSeasonStart, startingSeasonEnd, finalSeasonEnd, fileWriter);
        understatJoiner.joinPlayerData();
        understatJoiner.joinTeamData();
    }
}
