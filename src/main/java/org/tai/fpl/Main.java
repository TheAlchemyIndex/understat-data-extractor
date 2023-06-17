package org.tai.fpl;

import org.tai.fpl.config.FplConfig;
import org.tai.fpl.joiners.UnderstatJoiner;
import org.tai.fpl.understat.Understat;
import org.tai.fpl.writers.FileWriter;

public class Main {

    public static void main(String[] args) {
        FplConfig config = new FplConfig("src/main/resources/config.properties");
        final String season = config.getSeason();
        final String baseFilePath = config.getBaseFilePath();
        final String seasonFilePath = String.format("%s%s/", baseFilePath, season);

        FileWriter fileWriter = new FileWriter(baseFilePath, season);

        Understat understat = new Understat(fileWriter, season);
        understat.getTeamData();
        understat.getPlayerData();

        UnderstatJoiner understatJoiner = new UnderstatJoiner(2019, 2020, 2023);
        understatJoiner.joinPlayerData(fileWriter, baseFilePath, String.format("Understat - %s-%s seasons.csv", 2019, 23));
        understatJoiner.joinTeamData(fileWriter, baseFilePath, String.format("Understat Teams - %s-%s seasons.csv", 2019, 23));
    }
}
