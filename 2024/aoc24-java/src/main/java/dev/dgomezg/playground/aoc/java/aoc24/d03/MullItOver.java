package dev.dgomezg.playground.aoc.java.aoc24.d03;


import dev.dgomezg.playground.aoc.java.utils.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MullItOver {


    public static final String FILENAME = "d03-MullItOver/d03-dgomezg.txt";
    public static boolean isEnabled = true;

    public static void main(String[] args) throws IOException, URISyntaxException {

        Long collect = FileUtils.getLines(FILENAME)
                .flatMap(MullItOver::parse01)
                .mapToLong(Factors::result)
                .sum();
        System.out.println("Part 01: " + collect);

        // NOT 105264641
        // 103811193: Trick: the Don't and dos can be spread over multiple lines.
        long sum = FileUtils.getFileLines(FILENAME)
                .stream().flatMap(MullItOver::parse02)
                .mapToLong(Factors::result)
                .sum();

        System.out.println("Part02: " + sum);

    }

    public static Stream<Factors> parse01(String line) {
        System.out.println("Parsing line: " + line);

        return parseFactors(line);
    }

    public static Stream<Factors> parse02(String line) {
        Pattern pattern = Pattern.compile("(mul\\((\\d+),(\\d+)\\))|(do\\(\\))|(don't\\(\\))");
        Matcher matcher = pattern.matcher(line);

        ArrayList<Factors> muls = new ArrayList<>(matcher.groupCount());

        while (matcher.find()) {
            System.out.printf("\tFound match: %s :", matcher.group());
            if (matcher.group(1) != null && isEnabled) {
                Factors factors = new Factors(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
                System.out.printf(" -> %s%n", factors);
                muls.add(factors);
            } else {
                if (matcher.group(4) != null) {
                    isEnabled = true;
                } else if (matcher.group(5) != null) {
                    isEnabled = false;
                }
            }
        }
        return muls.stream();
    }

    private static Stream<Factors> parseFactors(String line) {
        Matcher matcher = Pattern.compile("mul\\(([0-9]*),([0-9]*)\\)")
                .matcher(line);
        ArrayList<Factors> muls = new ArrayList<>(matcher.groupCount());

        while (matcher.find()) {
            System.out.printf("\tfound %s ", matcher.group());
            Factors factors = new Factors(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
            System.out.printf(" -> %s%n", factors);
            muls.add(factors);
        }
        return muls.stream();
    }

    public record Factors(int a, int b){
        public String toString() {
            return a + " * " + b;
        }
        long result() {
            return a * b;
        }
    }
}
