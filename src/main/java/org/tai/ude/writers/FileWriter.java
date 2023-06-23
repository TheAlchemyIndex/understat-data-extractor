package org.tai.ude.writers;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileWriter {
    private final String baseFilePath;
    private final String season;

    public FileWriter(String baseFilePath, String season) throws IllegalArgumentException {
        if (baseFilePath == null | season == null) {
            throw new IllegalArgumentException("FileWriter initialised with null baseFilePath or null season.");
        }
        this.baseFilePath = baseFilePath;
        this.season = season;
    }

    public void writeDataToBasePath(JSONArray elements, String subFilePath) throws IOException {
        File file = new File(String.format("%s/%s", this.baseFilePath, subFilePath));
        String csvString = CDL.toString(elements);
        FileUtils.writeStringToFile(file, csvString, Charset.defaultCharset());
    }

    public void writeDataToSeasonPath(JSONArray elements, String subFilePath) throws IOException {
        File file = new File(String.format("%s/%s/%s", this.baseFilePath, this.season, subFilePath));
        String csvString = CDL.toString(elements);
        FileUtils.writeStringToFile(file, csvString, Charset.defaultCharset());
    }
}
