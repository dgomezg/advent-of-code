package dev.dgomezg.playground.aoc.java.aoc24.d08;

import java.util.*;

public class MapLayer {
    private Character symbol;
    private Set<Position> positions = new HashSet<>();

    public MapLayer(Character symbol, Collection<Position> positions) {
        this.symbol = symbol;
        this.positions.addAll(positions);
    }
    public Character getSymbol() {
        return symbol;
    }
    public Collection<Position> getPositions() {
        return Set.copyOf(positions);
    }
    public void addPosition(Position position) {
        positions.add(position);
    }
}
