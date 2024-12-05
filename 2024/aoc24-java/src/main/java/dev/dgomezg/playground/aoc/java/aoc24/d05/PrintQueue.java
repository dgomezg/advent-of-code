package dev.dgomezg.playground.aoc.java.aoc24.d05;


import dev.dgomezg.playground.aoc.java.utils.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrintQueue {

    private static HashMap<Integer, Set<Integer>> orderingRules = new HashMap<>();

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> fileLines = FileUtils.getFileLines("d05-PrintQueue/d05-dgomezg.txt");

        long result = 0L;
        long resultPart02 = 0L;

        Pattern orderingRulesPattern = Pattern.compile("(\\d+)\\|(\\d+)");
        Pattern printUpdatesPattern = Pattern.compile("((\\d+),*)");
        for (String line : fileLines) {
            System.out.println("Parsing line " + line);
            Matcher matcher = orderingRulesPattern.matcher(line);
            //If is ordering rule, add to ordering rules
            if (matcher.find()) {
                System.out.printf("   Line corresponds to an ordering rule");
                Integer firstPage = Integer.parseInt(matcher.group(1));
                Integer secondPage = Integer.parseInt(matcher.group(2));
                System.out.printf(": %d -> %d%n", firstPage, secondPage);
                if (!orderingRules.containsKey(firstPage)) {
                    orderingRules.put(firstPage, new TreeSet<Integer>());
                }
                orderingRules.get(firstPage).add(secondPage);
            } else { //Check if update is valid.
                //parse line into int[]
                Matcher printingRulesMatcher = printUpdatesPattern.matcher(line);
                if (line.trim().length() > 0) {
                    System.out.println("   Line corresponds to an update printing");
                    int[] updates = printingRulesMatcher.results()
                            .map(match -> match.group(2))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    // iterate checking validity
                    boolean isValid = true;
                    int currIdx = 0;
                    while (isValid && currIdx < updates.length) {
                        Set shouldBePrintedBefore = orderingRules.get(updates[currIdx]);
                        int idxCheck = currIdx - 1;
                        while (isValid && idxCheck > 0 && idxCheck < currIdx) {
                            isValid = shouldBePrintedBefore == null  || !shouldBePrintedBefore.contains(updates[idxCheck]);
                            idxCheck++;
                        }
                        idxCheck = currIdx + 1;
                        while (isValid && idxCheck < updates.length) {
                            isValid = shouldBePrintedBefore == null  || shouldBePrintedBefore.contains(updates[idxCheck]);
                            idxCheck++;
                        }
                        currIdx++;

                    }
                    if (isValid) {
                        System.out.printf("Line '%s' is valid. \tAdding %d%n", line, updates[updates.length / 2]);
                        result += updates[updates.length / 2];
                    } else {
                        //need to reorder the array.
                        System.out.printf("Invalid array %s", Arrays.toString(updates));
                        updates = Arrays.stream(updates)
                                .boxed()
                                .sorted((a,b) -> compare(a,b))
                                .mapToInt(Integer::intValue)
                                .toArray();

                        System.out.printf(" -> Sorted to %s", Arrays.toString(updates));
                        System.out.printf("\tAdding %d to part02 %n", updates[updates.length / 2]);
                        resultPart02 += updates[updates.length / 2];
                    }
                }
            }
        }

        System.out.println("Part 01 : " + result);

        // 4867 is too low
        System.out.println("Part 02 : " + resultPart02);
    }

    public static int compare(Integer o1, Integer o2) {
        if (o1.equals(o2)) {
            return 0;
        }

        Set shouldBePrintedBefore = orderingRules.get(o1);
        if (shouldBePrintedBefore == null || !shouldBePrintedBefore.contains(o2)) {
            return -1;
        }
        return 1;

    };
}
