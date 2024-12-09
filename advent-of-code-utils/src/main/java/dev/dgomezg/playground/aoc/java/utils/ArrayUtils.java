package dev.dgomezg.playground.aoc.java.utils;

import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

public class ArrayUtils {

    public static int[] removeElementAt(int[] array, int index) {
        int[] newArray = new int[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
        return newArray;
    }

    public static int[] prepend(int value, int[] array) {
        int[] newArray = new int[array.length + 1];
        newArray[0] = value;
        System.arraycopy(array, 0, newArray, 1, array.length);
        return newArray;
    }

    public static int[] generateArray(int value, int length) {
        return IntStream.generate(() -> value).limit(length).toArray();
    }
}
