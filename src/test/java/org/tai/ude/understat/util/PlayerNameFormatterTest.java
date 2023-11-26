package org.tai.ude.understat.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.tai.ude.understat.util.PlayerNameFormatter.formatName;

public class PlayerNameFormatterTest {

    private static final String TEST_NAME1 = "Ahmed Hegazy";
    private static final String EXPECTED_FORMATTED_NAME1 = "Ahmed%20Hegazi";

    private static final String TEST_NAME2 = "Zanka";
    private static final String EXPECTED_FORMATTED_NAME2 = "Mathias%20Jørgensen";

    private static final String TEST_NAME3 = "Lewis O&#039;Brien";
    private static final String EXPECTED_FORMATTED_NAME3 = "Lewis%20O%27Brien";

    private static final String TEST_NAME4 = "George Best";
    private static final String EXPECTED_FORMATTED_NAME4 = "George%20Best";

    /* Basic tests to test functionality */
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
    public void givenValidNameNotInSwitchStatement_formatName_thenReturnSameName() {
        assertEquals(EXPECTED_FORMATTED_NAME4, formatName(TEST_NAME4));
    }
}
