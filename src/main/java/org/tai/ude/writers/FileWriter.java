package org.tai.ude.writers;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.CDL;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileWriter {
    private static final Logger LOGGER = LogManager.getLogger(FileWriter.class);
    private final String baseFilePath;

    public FileWriter(String baseFilePath) {
        this.baseFilePath = baseFilePath;
    }

    public void write(JSONArray jsonData, String subFilePath) {
        if (subFilePath == null) {
            throw new RuntimeException("Sub file path can not be .");
        }
        try {
            File file = new File(String.format("%s/%s", this.baseFilePath, subFilePath));
            String dataString = CDL.toString(jsonData);
            FileUtils.writeStringToFile(file, dataString, Charset.defaultCharset());
            LOGGER.info(String.format("Write to {%s} complete.", subFilePath));
        } catch (IOException | NullPointerException exception) {
            throw new RuntimeException(String.format("Error writing data to {%s}: {%s}", subFilePath, exception.getMessage()));
        }
    }

    public String getBaseFilePath() {
        return baseFilePath;
    }
}
