package dev.dgomezg.playground.aoc.java.aoc24.d04;


import dev.dgomezg.playground.aoc.java.utils.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class CeresSearch {

    public static void main(String[] args) throws IOException, URISyntaxException {

        List<String> fileLines = FileUtils.getFileLines("d04-CeresSearch/d04-dgomezg.txt");

        int line = 0;
        int found = 0;

        for (String fileLine : fileLines) {
            System.out.println("Analizing line "+ line + " : " + fileLine);
            for (int column = 0; column < fileLine.length(); column++) {
                if (fileLine.charAt(column) == 'X') {
                    int inLine = searchFromPosition(fileLines, line, column);
                    found += inLine;
                    System.out.printf("\tFound %d at line %d column %d%n", inLine, line, column);
                }
            }
            line++;
        }

        //2642
        System.out.println("Part01: " + found);

        //Part 02
        line = 0;
        found = 0;
        for(String fileLine : fileLines) {
            System.out.println("Analizing line "+ line + " : " + fileLine);
            for (int column = 0; column < fileLine.length(); column++) {
                if (fileLine.charAt(column) == 'A') {
                    System.out.printf("\tFound 'A' at line %d column %d%n", line, column);
                    boolean diag1 =
                            search("MAS", fileLines, line -1, column -1, 1, 1)
                                    || search("MAS", fileLines, line+1,column+1, -1, -1);
                    boolean diag2 = search("MAS", fileLines, line-1, column +1, -1, 1)
                            || search("MAS", fileLines, line+1, column-1, +1, -1);
                    System.out.printf("Diagonal 1 %b and Diagonal 2 %b%n ", diag1, diag2);

                    if (diag1 && diag2) found++;

                }
            }
            line++;
        }

        System.out.println("Part02: " + found);
    }

    private static int searchFromPosition(List<String> fileLines, int lineNum, int column) {
        int found = 0;
        var fileLine = fileLines.get(lineNum);
        if (searchForward(fileLine, column))                   found++;
        if (searchDownForward(fileLines, lineNum, column))     found++;
        if (searchDown(fileLines, lineNum, column))            found++;
        if (searchDownBackwards(fileLines, lineNum, column))   found++;
        if (searchBackwards(fileLine, column))                 found++;
        if (searchUpBackwards(fileLines, lineNum, column))     found++;
        if (searchUp(fileLines, lineNum, column))              found++;
        if (searchUpForward(fileLines, lineNum, column))       found++;
        return found;
    }

    static boolean search(String search, List<String> lines, int lineNum, int column, int forwardInc,  int downInc) {

        if (lineNum < 0 || column < 0 || lineNum >= lines.size()) return false;

        boolean found = false;
        String line = lines.get(lineNum);
        int lineSize = line.length();

        //check if search is withing limits.
        found =  lineSize > column + (search.length()-1) * forwardInc
                && lines.size() > lineNum + (search.length()-1) * downInc
                && column + (search.length()-1) * forwardInc >= 0
                && lineNum + (search.length()-1) * downInc >= 0;

        //search for the string
        int currCharIdx = 0;
        while (found && currCharIdx < search.length()) {
            int currCol = column + currCharIdx * forwardInc;
            int currLine = lineNum + currCharIdx * downInc;
            System.out.printf("Checking in line num %d ('%s') if character at position %d is %s%n", currLine, lines.get(currLine), currCol, search.charAt(currCharIdx));
            found = currLine <= lines.size()
                    && lines.get(currLine).length() > currCol
                    && lines.get(currLine).charAt(currCol) == search.charAt(currCharIdx);
            currCharIdx++;
        }

        return found;
    }

    static boolean searchUp( List<String> lines, int lineNum, int columnNum) {
        System.out.println("Searching up..." + lineNum +", " + columnNum);
        return search("XMAS", lines, lineNum, columnNum, 0, -1);
    }

    static boolean searchUpBackwards( List<String> lines, int lineNum, int columnNum) {
        System.out.println("Searching up backwards..." + lineNum +", " + columnNum);
        return search("XMAS", lines, lineNum, columnNum, -1, -1);
    }

    static boolean searchUpForward( List<String> lines, int lineNum, int columnNum) {
        System.out.println("Searching up forward..." + lineNum +", " + columnNum);
        return search("XMAS", lines, lineNum, columnNum, 1, -1);
    }

    static boolean searchDown( List<String> lines, int lineNum, int columnNum) {
        System.out.println("Searching down..." + lineNum +", " + columnNum);
        return search("XMAS", lines, lineNum, columnNum, 0, 1);
    }

    static boolean searchDownBackwards( List<String> lines, int lineNum, int columnNum) {
        System.out.println("Searching down backwards..." + lineNum +", " + columnNum);
        return search("XMAS", lines, lineNum, columnNum, -1, 1);
    }

    public static boolean searchDownForward(List<String> lines, int linNum, int columnNum) {
        System.out.println("Searching down forward..." + linNum +", " + columnNum);
        return search("XMAS", lines, linNum, columnNum, 1, 1);
    }

    static boolean searchBackwards(String fileLine, int position) {
        System.out.println("Searching backwards..." + position);
        return search("XMAS", List.of(fileLine), 0, position, -1, 0);
    }

    static boolean searchForward(String fileLine, int position) {
        System.out.println("Searching forward..." + position);
        return search("XMAS", List.of(fileLine), 0, position, +1, 0);
    }

}
