package org.tai.ude.joiners;

import org.junit.Test;
import org.tai.ude.TestHelper;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class UnderstatJoinerTest extends TestHelper {
    private static UnderstatJoiner UNDERSTAT_JOINER;
    private static final String JOINED_UNDERSTAT_PLAYER_FILEPATH = String.format("%s%s", BASE_FILEPATH, JOINED_UNDERSTAT_PLAYERS_FILENAME);
    private static final String JOINED_UNDERSTAT_TEAM_FILEPATH = String.format("%s%s", BASE_FILEPATH, JOINED_UNDERSTAT_TEAMS_FILENAME);

    @Test
    public void givenValidSeason_joinPlayerData_thenWriteToFile() {
        UNDERSTAT_JOINER = new UnderstatJoiner(VALID_STARTING_SEASON_START, VALID_STARTING_SEASON_END, VALID_ENDING_SEASON_END,
                FILE_WRITER);
        UNDERSTAT_JOINER.joinPlayerData();

        File joinedUnderstatPlayers = new File(JOINED_UNDERSTAT_PLAYER_FILEPATH);
        assertTrue(joinedUnderstatPlayers.exists());

        assertTrue(readDataFromFile(JOINED_UNDERSTAT_PLAYER_FILEPATH).similar(VALID_JSON_ARRAY));
    }

    @Test
    public void givenValidSeason() {
//        givenValidSeason_joinTeamData_thenWriteToFile
        UNDERSTAT_JOINER = new UnderstatJoiner(VALID_STARTING_SEASON_START, VALID_STARTING_SEASON_END, VALID_ENDING_SEASON_END,
                FILE_WRITER);
        UNDERSTAT_JOINER.joinTeamData();

        File joinedUnderstatTeams = new File(JOINED_UNDERSTAT_TEAM_FILEPATH);
        assertTrue(joinedUnderstatTeams.exists());

        assertTrue(readDataFromFile(JOINED_UNDERSTAT_TEAM_FILEPATH).similar(VALID_JSON_ARRAY));
    }

    @Test(expected = RuntimeException.class)
    public void givenNonExistentSeason_joinPlayerData_thenThrowRuntimeException() {
        UNDERSTAT_JOINER = new UnderstatJoiner(INVALID_STARTING_SEASON_START, INVALID_STARTING_SEASON_END, INVALID_ENDING_SEASON_END, FILE_WRITER);
        UNDERSTAT_JOINER.joinPlayerData();
    }

    @Test(expected = RuntimeException.class)
    public void givenNonExistentSeason_joinTeamData_thenThrowRuntimeException() {
        UNDERSTAT_JOINER = new UnderstatJoiner(INVALID_STARTING_SEASON_START, INVALID_STARTING_SEASON_END, INVALID_ENDING_SEASON_END, FILE_WRITER);
        UNDERSTAT_JOINER.joinTeamData();
    }
}
