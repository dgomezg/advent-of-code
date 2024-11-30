package dev.dgomezg.playground.aoc.java.utils;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {


    public static final String NON_EXISTING_FILE = "NonExistingFile.txt";
    public static final String EXISTING_FILE = "sample.txt";

    @Test
    void shouldThrowAnExceptionWhenFileDoesNotExist()  throws Exception {
        String fileName = NON_EXISTING_FILE;
        var exception = assertThrows(FileNotFoundException.class,
                () -> FileUtils.getSourceFilePath(fileName)
        );
        assertTrue(exception.getMessage().contains(fileName));

    }
    @Test
    void getSourceFilePath() throws FileNotFoundException, URISyntaxException {
        Path sourceFilePath = FileUtils.getSourceFilePath(EXISTING_FILE);
        assertNotNull(sourceFilePath);
    }

    @Test
    void getFileLines() throws IOException, URISyntaxException {
        List<String> fileLines = FileUtils.getFileLines(EXISTING_FILE);
        assertNotNull(fileLines);
        assertEquals(3, fileLines.size());
    }

    @Test
    void getLines() throws IOException, URISyntaxException {
        Stream<String> lines = FileUtils.getLines(EXISTING_FILE);
        assertNotNull(lines);
        assertEquals(3,lines.count());
    }
}