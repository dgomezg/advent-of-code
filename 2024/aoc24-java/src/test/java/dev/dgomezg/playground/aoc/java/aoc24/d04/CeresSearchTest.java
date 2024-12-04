package dev.dgomezg.playground.aoc.java.aoc24.d04;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CeresSearchTest {


    @Test
    void shouldFindXMASForward() {
        assertFalse(CeresSearch.searchForward("....XXMAS.",1));
        assertFalse(CeresSearch.searchForward("....XXMAS.",4));
        assertTrue(CeresSearch.searchForward("....XXMAS.",5));

        assertTrue(CeresSearch.searchForward("XMAS.S",0));

    }

    @Test
    void shouldFindXMASBackward() {
        assertTrue(CeresSearch.searchBackwards(".SAMXMS...",4));
        assertFalse(CeresSearch.searchBackwards("AMX....XXMAS.",2));
    }

    @Test
    void shouldFindXmasUpWard() {
        List<String> lines = List.of("S", "A", "M", "X");
        assertTrue(CeresSearch.searchUp(lines, 3, 0));
        assertFalse(CeresSearch.searchUp(lines, 2, 0));

        lines = List.of(".S", ".A", ".M", ".X");
        assertTrue(CeresSearch.searchUp(lines, 3, 1));
        assertFalse(CeresSearch.searchUp(lines, 2, 1));
        assertFalse(CeresSearch.searchUp(lines, 3, 0));
    }

    @Test
    void shouldFindXmasDownward() {
        List<String> lines = List.of("X", "M", "A", "S");
        assertTrue(CeresSearch.searchDown(lines, 0, 0));
        assertFalse(CeresSearch.searchDown(lines, 0, 1));
        assertFalse(CeresSearch.searchDown(lines, 1, 0));

        lines = List.of(".X", ".M", ".A", ".S");
        assertTrue(CeresSearch.searchDown(lines, 0, 1));
        assertFalse(CeresSearch.searchDown(lines, 1, 0));
        assertFalse(CeresSearch.searchDown(lines, 1, 1));
    }

    @Test
    void shouldFindXmasUpBackards() {
        List<String> lines = List.of("S...", ".A..", "..M.", "...X");
        assertTrue(CeresSearch.searchUpBackwards(lines, 3, 3));
        assertFalse(CeresSearch.searchUpBackwards(lines, 3, 0));
    }

    @Test
    void shouldFindXmasUpForward() {
        List<String> lines = List.of("...S", "..A.", ".M..", "X...");
        assertTrue(CeresSearch.searchUpForward(lines, 3, 0));
    }

    @Test
    void shouldFindXmasDownBackwards() {
        List<String> lines =
                List.of(
                        "...X",
                        "..M.",
                        ".A..",
                        "S...");
        assertTrue(CeresSearch.searchDownBackwards(lines, 0, 3));
    }

    @Test
    void shouldFindXmasDownForward() {
        List<String> lines =
                List.of(
                        "X...",
                        ".M..",
                        "..A.",
                        "...S"
                );
        assertTrue(CeresSearch.searchDownForward(lines, 0, 0));

    }

}