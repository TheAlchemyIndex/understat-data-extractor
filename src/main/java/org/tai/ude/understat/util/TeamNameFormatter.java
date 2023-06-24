package org.tai.ude.understat.util;

public class TeamNameFormatter {

    public static String formatName(String teamName) {
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
