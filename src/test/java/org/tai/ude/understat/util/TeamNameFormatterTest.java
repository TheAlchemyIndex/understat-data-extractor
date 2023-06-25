package org.tai.ude.understat.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.tai.ude.understat.util.TeamNameFormatter.formatName;

public class TeamNameFormatterTest {

    private static final String TEST_NAME1 = "Cardiff";
    private static final String EXPECTED_FORMATTED_NAME1 = "Cardiff City";

    private static final String TEST_NAME2 = "Manchester City";
    private static final String EXPECTED_FORMATTED_NAME2 = "Man City";

    private static final String TEST_NAME3 = "Manchester United";
    private static final String EXPECTED_FORMATTED_NAME3 = "Man Utd";

    private static final String TEST_NAME4 = "Newcastle United";
    private static final String EXPECTED_FORMATTED_NAME4 = "Newcastle";

    private static final String TEST_NAME5 = "Nottingham Forest";
    private static final String EXPECTED_FORMATTED_NAME5 = "Nott'm Forest";

    private static final String TEST_NAME6 = "Sheffield United";
    private static final String EXPECTED_FORMATTED_NAME6 = "Sheffield Utd";

    private static final String TEST_NAME7 = "Tottenham";
    private static final String EXPECTED_FORMATTED_NAME7 = "Spurs";

    private static final String TEST_NAME8 = "West Bromwich Albion";
    private static final String EXPECTED_FORMATTED_NAME8 = "West Brom";

    private static final String TEST_NAME9 = "Wolverhampton Wanderers";
    private static final String EXPECTED_FORMATTED_NAME9 = "Wolves";

    private static final String TEST_NAME10 = "Linfield";

    @Test
    public void givenValidName_formatName_thenReturnFormattedName_1() {
        assertEquals(EXPECTED_FORMATTED_NAME1, formatName(TEST_NAME1));
    }

    @Test
    public void givenValidName_formatName_thenReturnFormattedName_2() {
        assertEquals(EXPECTED_FORMATTED_NAME2, formatName(TEST_NAME2));
    }

    @Test
    public void givenValidName_formatName_thenReturnFormattedName_3() {
        assertEquals(EXPECTED_FORMATTED_NAME3, formatName(TEST_NAME3));
    }

    @Test
    public void givenValidName_formatName_thenReturnFormattedName_4() {
        assertEquals(EXPECTED_FORMATTED_NAME4, formatName(TEST_NAME4));
    }

    @Test
    public void givenValidName_formatName_thenReturnFormattedName_5() {
        assertEquals(EXPECTED_FORMATTED_NAME5, formatName(TEST_NAME5));
    }

    @Test
    public void givenValidName_formatName_thenReturnFormattedName_6() {
        assertEquals(EXPECTED_FORMATTED_NAME6, formatName(TEST_NAME6));
    }

    @Test
    public void givenValidName_formatName_thenReturnFormattedName_7() {
        assertEquals(EXPECTED_FORMATTED_NAME7, formatName(TEST_NAME7));
    }

    @Test
    public void givenValidName_formatName_thenReturnFormattedName_8() {
        assertEquals(EXPECTED_FORMATTED_NAME8, formatName(TEST_NAME8));
    }

    @Test
    public void givenValidName_formatName_thenReturnFormattedName_9() {
        assertEquals(EXPECTED_FORMATTED_NAME9, formatName(TEST_NAME9));
    }

    @Test
    public void givenValidNameNotInSwitchStatement_formatName_thenReturnSameName() {
        assertEquals(TEST_NAME10, PlayerNameFormatter.formatName(TEST_NAME10));
    }
}
