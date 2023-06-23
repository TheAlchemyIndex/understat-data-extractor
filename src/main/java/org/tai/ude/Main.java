package org.tai.ude;

import org.tai.ude.config.UnderstatConfig;
import org.tai.ude.joiners.UnderstatJoiner;
import org.tai.ude.understat.Understat;
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

        FileWriter fileWriter = new FileWriter(baseFilePath, mainSeason);

        Understat understat = new Understat(fileWriter, mainSeason);
        understat.getTeamData();
        understat.getPlayerData();

        UnderstatJoiner understatJoiner = new UnderstatJoiner(startingSeasonStart, startingSeasonEnd, finalSeasonEnd);
        understatJoiner.joinPlayerData(fileWriter, baseFilePath, String.format("Understat - %s-%s seasons.csv", startingSeasonStart, finalSeasonEnd));
        understatJoiner.joinTeamData(fileWriter, baseFilePath, String.format("Understat Teams - %s-%s seasons.csv", startingSeasonStart, finalSeasonEnd));
    }
}
