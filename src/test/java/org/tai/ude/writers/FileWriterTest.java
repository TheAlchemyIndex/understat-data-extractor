package org.tai.ude.writers;

import org.junit.Test;
import org.tai.ude.TestHelper;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class FileWriterTest extends TestHelper {
    private static FileWriter FILE_WRITER;
    private static final String EXPECTED_FILEPATH = "src/test/resources/data/test_filename.csv";

    @Test
    public void givenValidJSONArrayAndValidFilePath_write_thenCreateCsvFile() {
        FILE_WRITER = new FileWriter(BASE_FILEPATH);
        FILE_WRITER.write(VALID_JSON_ARRAY, FILE_WRITER_FILENAME);

        File gameweekFile = new File(EXPECTED_FILEPATH);
        assertTrue(gameweekFile.exists());
        assertTrue(readDataFromFile(EXPECTED_FILEPATH).similar(VALID_JSON_ARRAY));
    }

    @Test(expected = RuntimeException.class)
    public void givenNullJSONArrayAndValidFilePath_write_thenThrowRuntimeException() {
        FILE_WRITER = new FileWriter(BASE_FILEPATH);
        FILE_WRITER.write(null, FILE_WRITER_FILENAME);
    }

    @Test(expected = RuntimeException.class)
    public void givenValidJSONArrayAndNullFilePath_write_thenThrowRuntimeException() {
        FILE_WRITER = new FileWriter(BASE_FILEPATH);
        FILE_WRITER.write(VALID_JSON_ARRAY, null);
    }

    @Test(expected = RuntimeException.class)
    public void givenNullJSONArrayAndNullFilePath_write_thenThrowRuntimeException() {
        FILE_WRITER = new FileWriter(BASE_FILEPATH);
        FILE_WRITER.write(null, null);
    }
}
