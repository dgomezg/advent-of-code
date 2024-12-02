package dev.dgomezg.playground.aoc.java.aoc24.d02;


import dev.dgomezg.playground.aoc.java.utils.ArrayUtils;
import dev.dgomezg.playground.aoc.java.utils.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Stream;

public class RedNosedReports {


    public static final String INPUT_FILE_NAME = "d02-RedNosedReports/d02-dgomezg.txt";

    public static void main(String[] args) throws IOException, URISyntaxException {

        Stream<int[]> input = FileUtils.getLines(INPUT_FILE_NAME)
                .map(RedNosedReports::parseLine);

        //572
        long count = input.filter(RedNosedReports::isCompletelySafe)
                .count();
        System.out.printf("Part 01 : %d%n", count);


        //612
        input = FileUtils.getLines(INPUT_FILE_NAME)
                .map(RedNosedReports::parseLine);
        count = input.filter(RedNosedReports::isSafeWithTolerance)
                .count();
        System.out.printf("Part 02 : %d%n", count);


    }



    public static boolean isSafeWithTolerance(int[] input) {
        int direction = input[0] > input[1]? -1 : 1;
        System.out.printf("---- Checking line %s. Detected direction is %+d\t", Arrays.toString(input), direction);

        for (int i = 0; i < input.length - 1; i++) {
            if (!isSafe(input[i], input[i+1], direction)) {
                System.out.printf("%n\tUNSAFE sequence detected: %d & %d. Checking if is safe without element %d or without %d%n",
                        input[i], input[i+1], input[i], input[i+1]);
                if (isCompletelySafe(ArrayUtils.removeElementAt(input, i))
                        || isCompletelySafe(ArrayUtils.removeElementAt(input, i+1))) {
                    System.out.printf("Line is SAFE after removing one of the elements%n");
                    return true;
                } else {
                    System.out.printf("Line is STILL UNSAFE after removing one of the elements%n");
                    return false;
                }

            }
        }
        System.out.println("Line is SAFE");
        return true;
    }

    private static boolean isSafe(int a, int b, int direction) {
        int dif = b*direction - a*direction;
        return dif > 0 && dif <= 3;
    }

    public static boolean isCompletelySafe(int[] input) {
        int direction = input[0] > input[1]? -1 : 1;
        System.out.printf("Checking line %s. Detected direction is %+d\t", Arrays.toString(input), direction);
        for (int i = 0; i < input.length-1; i++) {
            if (!isSafe(input[i], input[i+1], direction)) {
                System.out.printf("UNSAFE sequence detected: %d & %d %n", input[i], input[i+1]);
                return false;
            }
        }
        System.out.println("Line is SAFE");
        return true;
    }

    public static int[] parseLine(String line) {
        return Arrays.stream(line.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }


}
