package dev.dgomezg.playground.aoc.java.aoc24.d01;


import dev.dgomezg.playground.aoc.java.utils.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HistorianHysteria {

    public static void main(String[] args) throws IOException, URISyntaxException {

        List<Integer> set1 = new ArrayList<>();
        List<Integer> set2 = new ArrayList<>();

        FileUtils.getLines("d01-HistorianHysteria/d01.01-dgomezg.txt")
                .map(HistorianHysteria::parse)
                .peek(arr -> System.out.printf("%d %d%n", arr[0], arr[1]))
                .forEach(arr -> {
                    set1.add(arr[0]);
                    set2.add(arr[1]);
                });

        long diff = part01Algorithm(set1, set2);

        System.out.printf("Part 01: %d%n", diff);

        System.out.printf("Part 02: %d%n", calculateSimilarityScore(set1, set2));

    }

    private static long calculateSimilarityScore(List<Integer> set1, List<Integer> set2) {
        HashMap<Integer, Integer> occurrences = new HashMap<>();
        set2.stream().forEach(i -> {
            if (!occurrences.containsKey(i)) {
                occurrences.put(i, 1);
            } else {
                occurrences.put(i, occurrences.get(i) + 1);
            }
        });

        long total = 0L;
        for (Integer i : set1) {
            if (occurrences.containsKey(i)) {
                total += i * occurrences.get(i);
            }
        }

        return total;
    }

    private static long part01Algorithm(List<Integer> set1, List<Integer> set2) {
        int[] arr1 = set1.stream().mapToInt(Integer::intValue).toArray();
        int[] arr2 = set2.stream().mapToInt(Integer::intValue).toArray();

        Arrays.sort(arr1);
        Arrays.sort(arr2);

        long diff = 0L;
        for (int i = 0; i < arr1.length; i++) {
            System.out.printf("adding Difference from %d - %d = %d%n", arr1[i], arr2[i], Math.abs(arr1[i] - arr2[i]));
            diff += Math.abs(arr1[i] - arr2[i]);
        }
        return diff;
    }

    public static int[] parse(String line) {
        System.out.printf("Parsing '%s'%n", line);
        Pattern compile = Pattern.compile("([0-9]+)( +)([0-9]+)");
        Matcher matcher = compile.matcher(line);
        if (matcher.matches()) {
            return new int[]{Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(3))};
        }
        return new int[]{0, 0};

    }
}
