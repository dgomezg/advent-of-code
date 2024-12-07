package dev.dgomezg.playground.aoc.java.aoc24.d07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestValue {
    private long result;
    private int[] values;

    public TestValue(String line) {
        this.result = Long.parseLong(line.substring(0, line.indexOf(":")));
        values = Arrays.stream(line.substring(line.indexOf(":") + 2).split(" "))
                .mapToInt(s -> Integer.parseInt(s))
                .toArray();
    }

    public long getResult() {
        return result;
    }

    public TestValue(long result, int[] values) {
        this.result = result;
        this.values = values;
    }

    public boolean isValidPart01() {
        return isValid("*+");
    }

    public boolean isValidPart02() {
        return isValid("*+|");
    }

    public boolean isValid(String operators) {
        System.out.printf("Finding if %d: %s has a solution%n", result, Arrays.toString(values));

        int operatorSpaces = values.length - 1;
        System.out.printf("\tThere are %d positions for operators%n", values.length - 1);

        if (operatorSpaces <= 0) {
            return result == values[0];
        }

        System.out.printf("\tThere are %d different posible combinations%n", (int) Math.pow(operators.length(), operatorSpaces));
        List<String> posibleCombinations = findCombinations(operatorSpaces, operators);
        //posibleCombinations.forEach(System.out::println);

        for (String operations : posibleCombinations) {
            //System.out.printf("Combination: %s ", operations);
            long partialResult = values[0];
            for (int i = 1; i < values.length; i++) {
                switch (operations.charAt(i - 1)) {
                    case '*':
                        partialResult *= values[i];
                        break;
                    case '+':
                        partialResult += values[i];
                        break;
                    case '|':
                        partialResult = Long.parseLong("" + partialResult + values[i]);
                        break;
                }
                if (partialResult > result) {
                    break;
                }
            }
            if (partialResult == result) {
                System.out.printf("Combination: %s ", operations);
                System.out.printf("\t results in %d -> %s%n", partialResult, partialResult == result ? "isValid" : "is not valid");
                return true;
            }
        }
        return false;
    }

    private List<String> findCombinations(int length) {
        return findCombinations(length, "*+");
    }

    private List<String> findCombinations(int length, String operators) {
        if (length <= 1) {
            List<String> result = new ArrayList<>(operators.length());
            for (int i = 0; i < operators.length(); i++) {
                result.add(String.valueOf(operators.charAt(i)));
            }
            return result;
        }

        ArrayList<String> operations = new ArrayList<>((int) Math.pow(operators.length(), length));
        List<String> tail = findCombinations(length - 1, operators);
        for (int i = 0; i < operators.length(); i++) {
            String operation = String.valueOf(operators.charAt(i));
            operations.addAll(
                    tail.stream()
                            .map(s -> operation + s)
                            .toList());
        }
        return operations;

    }

}