package dev.dgomezg.playground.aoc.java.aoc24.d07;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestValueTest {

    public static String OPERATORS_PART_01 = "*+";

    @Test
    void shouldDetectInvalidLinesWithOneValues() {
        TestValue value = new TestValue(19, new int[]{10});
        assertFalse(value.isValid(OPERATORS_PART_01));
    }

    @Test
    void shouldDetectValidLinesWithOneValue() {
        TestValue value = new TestValue(19, new int[]{19});
        assertTrue(value.isValid(OPERATORS_PART_01));
    }
    @Test
    void shouldDetectValidLinesWithTwoValues() {
        TestValue value = new TestValue(190, new int[]{10, 19});
        assertTrue(value.isValid(OPERATORS_PART_01));
    }
    @Test
    void shouldDetectinvalidLinesWithTwoValues() {
        TestValue value = new TestValue(83, new int[]{17, 5});
        assertFalse(value.isValid(OPERATORS_PART_01));
    }

    @Test
    void shouldDetectValidLinesWithThreeValues() {
        TestValue value = new TestValue(3267, new int[]{81,40,27});
        assertTrue(value.isValid(OPERATORS_PART_01));
    }

    @Test
    void shouldDetectInvalidLinesWithThreeValues() {
        TestValue value = new TestValue(192, new int[]{17,8,14});
        assertFalse(value.isValid(OPERATORS_PART_01));
    }

    @Test
    void shouldDetectValidLinesWithFourOrMoreValues() {
        TestValue value = new TestValue(292, new int[]{ 11,6,16,20});
        assertTrue(value.isValid(OPERATORS_PART_01));
    }

    @Test
    void shouldDetectInvalidLinesWithFourOrMoreValues() {
        TestValue value = new TestValue(21037, new int[]{ 9,7,18,13});
        assertFalse(value.isValid(OPERATORS_PART_01));
    }

}