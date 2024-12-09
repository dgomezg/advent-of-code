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

        diskFragmenter.reset();
        diskFragmenter.deFragmentUsingFullFiles();
        System.out.printf("%nPart 02: %d%n",diskFragmenter.checksum());

    }

    private final String fragmented;
    private int[] expanded;
    private int[] defragmented;

    public DiskFragmenter(String fragmented) {
        this.fragmented = fragmented;
    }
    public void reset() {
        expanded = null;
        defragmented = null;
    }

    public String deFragmentUsingFullFiles() {
        if (expanded == null) {
            this.expand();
        }
        this.defragmented = Arrays.copyOf(expanded, expanded.length);

        int insertIdx = getInsertIdx(0, defragmented);
        int readIdx = getReadIdx(expanded.length - 1, insertIdx, defragmented);


        while (insertIdx < readIdx) {
            int fileSize = calculateFileSize(readIdx, defragmented);
            int destination = findfirstGapBigEnough(insertIdx, readIdx, fileSize, defragmented);
            if (destination == -1) {
                //There is no place to move the file at position readIdx
                // we advance the readIdx pointer
                insertIdx = getInsertIdx(0, defragmented);
                readIdx = getReadIdx(readIdx - fileSize, insertIdx, defragmented);
            } else {
                //The last file can be moved completely
                // move the to the destination and advance the reading pointer
                insertIdx = destination;
                moveFile(readIdx, insertIdx, fileSize, defragmented);
                readIdx = getReadIdx(readIdx - fileSize +1, insertIdx, defragmented);
                insertIdx = getInsertIdx(0, defragmented);
            }

        }

        return diskContentToString(this.defragmented);

    }

    private int findfirstGapBigEnough(int insertIdx, int readIdx, int fileSize, int[] defragmented) {
        int gapSize = calculatGapSize(insertIdx, defragmented);
        while (insertIdx < readIdx && gapSize < fileSize) {
            insertIdx = getInsertIdx(insertIdx + gapSize, defragmented);
            gapSize = calculatGapSize(insertIdx, defragmented);
        }
        if (insertIdx >= readIdx) {
            return -1;
        }
        return insertIdx;
    }

    private void moveFile(int from, int to, int size, int[] defragmented) {
        System.arraycopy(defragmented, from - size +1, defragmented, to, size);
        System.arraycopy(ArrayUtils.generateArray(-1, size), 0, defragmented, from - size + 1, size);
    }

    private int calculatGapSize(int idx, int[] defragmented) {
        int gapSize = 0;

        while (idx + gapSize < defragmented.length && defragmented[idx+gapSize ] < 0) {
            gapSize++;
        }
        return gapSize;
    }

    private int calculateFileSize(int readIdx, int[] defragmented) {
        int fileSize = 0;
        int fileID = defragmented[readIdx];
        while(readIdx - fileSize >= 0 && defragmented[readIdx - fileSize] == fileID) {
            fileSize ++;
        }
        return fileSize;
    }

    public String deFragment() {
        if (expanded == null) {
            this.expand();
        }
        this.defragmented = Arrays.copyOf(expanded, expanded.length);

        int insertIdx = getInsertIdx(0, defragmented);
        int readIdx = getReadIdx(expanded.length - 1, insertIdx, defragmented);

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
                .collect(Collectors.joining(""));
    }

    public long checksum() {
        if (defragmented == null) {
            deFragment();
        }

        long result = 0L;
        int idx = 0;
        while (idx < defragmented.length ) {
            if (defragmented[idx] != -1) {
                result += defragmented[idx] * idx;
            }
            idx++;
        }
        return result;
    }
}
