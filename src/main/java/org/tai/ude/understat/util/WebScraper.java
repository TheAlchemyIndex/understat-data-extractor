package org.tai.ude.understat.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class WebScraper {
    private static final Logger LOGGER = LogManager.getLogger(WebScraper.class);

    private final String url;

    public WebScraper(String url) {
        this.url = url;
    }

    public String getTargetElements(String targetVar) {
        try {
            Document doc = Jsoup.connect(this.url).get();
            Elements scriptElements = doc.select("script");
            String rawDataString = extractScriptElements(scriptElements, targetVar);
            return cleanScriptElement(rawDataString);
        } catch (HttpStatusException httpStatusException) {
            throw new RuntimeException(String.format("Error accessing the url {%s}: %s", this.url,
                    httpStatusException.getMessage()));
        } catch (IOException ioException) {
            throw new RuntimeException(String.format("IO error occurred while fetching data from the url {%s}: %s", this.url,
                    ioException.getMessage()));
        }
    }

    private String extractScriptElements(Elements scriptElements, String targetVar) {
        for (Element script : scriptElements) {
            String scriptHtml = script.html();
            if (scriptHtml.contains(targetVar)) {
                return scriptHtml;
            }
        }
        return "";
    }

    private String cleanScriptElement(String rawDataString) {
        try {
            String cleanedScriptElement = "";
            Pattern pattern = Pattern.compile("\\('(.*?)'\\)");
            Matcher matcher = pattern.matcher(rawDataString);
            if (matcher.find()) {
                cleanedScriptElement = matcher.group(1);
            }
            return cleanedScriptElement;
        } catch (PatternSyntaxException patternSyntaxException) {
            throw new RuntimeException(String.format("Invalid regular expression pattern provided: %s",
                    patternSyntaxException.getMessage()));
        }
    }
}
