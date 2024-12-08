package dev.dgomezg.playground.aoc.java.aoc24.d08;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void shouldCalculateDistanceTo() {
        Position a = new Position(1, 1);
        Position b = new Position(2, 2);
        assertEquals(new Position (1,1), a.distanceTo(b));
        assertEquals(new Position (-1,-1), b.distanceTo(a));

        Position c = new Position(1, 2);
        assertEquals(new Position (0,1), a.distanceTo(c));
        assertEquals(new Position (0,-1), c.distanceTo(a));

        Position d = new Position(4, 5);
        assertEquals(new Position(3,4), a.distanceTo(d));
        assertEquals(new Position(-3,-4), d.distanceTo(a));
    }
}