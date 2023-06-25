package org.tai.ude.understat.util;

import org.junit.Test;

public class WebScraperTest {

    @Test(expected = RuntimeException.class)
    public void givenInvalidUrl_getTargetElements_thenThrowRuntimeException() {
        String invalidUrl = "https://invalidurl";
        WebScraper scraper = new WebScraper(invalidUrl);
        scraper.getTargetElements("var testData");
    }
}
