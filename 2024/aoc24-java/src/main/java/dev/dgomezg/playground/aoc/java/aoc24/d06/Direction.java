package dev.dgomezg.playground.aoc.java.aoc24.d06;

enum Direction {
    UP('^'),
    RIGHT('>'),
    DOWN('v'),
    LEFT('<');

    char value;

    Direction(char value) {
        this.value = value;
    }
}
