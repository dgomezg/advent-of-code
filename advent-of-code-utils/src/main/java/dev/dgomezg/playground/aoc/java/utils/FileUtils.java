package dev.dgomezg.playground.aoc.java.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class FileUtils {

    public static Path getSourceFilePath(String fileName) throws URISyntaxException, FileNotFoundException {
        URL resource = FileUtils.class.getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new FileNotFoundException(fileName);
        }
        Path file = Path.of(resource.toURI());
        return file;
    }

    public static List<String> getFileLines(String filename) throws IOException, URISyntaxException {
        return getLines(filename)
                .toList();
    }

    public static Stream<String> getLines(String filename) throws IOException, URISyntaxException {
        return Files.lines(FileUtils.getSourceFilePath(filename));
    }

}
