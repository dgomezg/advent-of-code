package dev.dgomezg.playground.aoc.java.aoc24.d08;

public record Position(int row, int column) {

    Position distanceTo(Position position) {
        return new Position(position.row() - row, position.column() - column);
    }

    Position moveTo(Position position) {
        return new Position(row + position.row(), column +position.column());
    }

    Position reverse() {
        return new Position( -row, -column);
    }

    boolean isInsideMap(char[][] map) {
        return row >= 0 && row < map.length && column >= 0 && column < map[0].length;
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
