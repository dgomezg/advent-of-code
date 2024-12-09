package dev.dgomezg.playground.aoc.java.aoc24.d09;

import dev.dgomezg.playground.aoc.java.utils.ArrayUtils;
import dev.dgomezg.playground.aoc.java.utils.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DiskFragmenter {

    public static void main(String[] args) throws IOException, URISyntaxException {

        List<String> fileLines = FileUtils.getFileLines("d09-DiskFragmenter/d09-dgomezg.txt");

        DiskFragmenter diskFragmenter = new DiskFragmenter(fileLines.get(0));
        System.out.printf("%nPart 01: %d%n", diskFragmenter.checksum());


    }

    private final String fragmented;
    private int[] expanded;
    private int[] defragmented;

    public DiskFragmenter(String fragmented) {
        this.fragmented = fragmented;
    }

    public String deFragment() {
        if (expanded == null) {
            this.expand();
        }
        this.defragmented = Arrays.copyOf(expanded, expanded.length);

        int insertIdx = 0;
        insertIdx = getInsertIdx(insertIdx, defragmented);
        int readIdx = expanded.length - 1;
        getReadIdx(readIdx, insertIdx, defragmented);

        while (insertIdx < readIdx) {
            defragmented[insertIdx] = defragmented[readIdx];
            defragmented[readIdx] = -1;
            insertIdx = getInsertIdx(insertIdx, defragmented);
            readIdx = getReadIdx(readIdx, insertIdx, defragmented);
        }

        return diskContentToString(this.defragmented);
    }

    private int getReadIdx(int readIdx, int insertIdx, int[] expanded) {
        while (readIdx > insertIdx && expanded[readIdx] == -1) {
            readIdx--;
        }
        return readIdx;
    }

    private int getInsertIdx(int insertIdx, int[] expanded) {
        while (insertIdx <= expanded.length && expanded[insertIdx] != -1) {
            insertIdx++;
        }
        return insertIdx;
    }

    String expand() {
        StringBuilder result = new StringBuilder();
        int[][] diskExpanded = new int[fragmented.length()][];
        char[] charArray = fragmented.toCharArray();

        int fileId = 0;
        for (int i = 0; i < charArray.length; i++) {
            int diskBlockValue = -1;
            if (i % 2 == 0) {
                diskBlockValue = fileId;
                fileId++;
            }
            diskExpanded[i] = ArrayUtils.generateArray(diskBlockValue, Character.getNumericValue(charArray[i]));
        }

        this.expanded = Arrays.stream(diskExpanded)
                .flatMapToInt(arr -> Arrays.stream(arr))
                .toArray();

        return diskContentToString(this.expanded);

    }

    private String diskContentToString(int[] content) {
        return Arrays.stream(content)
                .mapToObj(i -> i < 0 ? "." : Integer.toString(i))
                .peek(value -> System.out.printf("%s", value))
                .collect(Collectors.joining(""));
    }

    public long checksum() {
        if (defragmented == null) {
            deFragment();
        }

        long result = 0L;
        int idx = 0;
        while (idx < defragmented.length && defragmented[idx] != -1) {
            result += defragmented[idx] * idx;
            idx++;
        }
        return result;
    }
}
