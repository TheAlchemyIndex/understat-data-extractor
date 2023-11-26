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
    private final String folderPath;

    public FileWriter(String folderPath) {
        this.folderPath = folderPath;
    }

    public void write(JSONArray jsonData, String filename) {
        if (filename == null) {
            throw new RuntimeException("Filename can not be null.");
        }
        try {
            File file = new File(String.format("%s/%s", this.folderPath, filename));
            String dataString = CDL.toString(jsonData);
            FileUtils.writeStringToFile(file, dataString, Charset.defaultCharset());
            LOGGER.info(String.format("Write to {%s} complete.", filename));
        } catch (IOException | NullPointerException exception) {
            throw new RuntimeException(String.format("Error writing data to {%s}: {%s}", filename, exception.getMessage()));
        }
    }
}
