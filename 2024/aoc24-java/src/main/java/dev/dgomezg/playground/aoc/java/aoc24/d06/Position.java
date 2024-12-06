package dev.dgomezg.playground.aoc.java.aoc24.d06;

record Position(int row, int column) {
    Position move(Direction direction) {
        return switch (direction) {
            case UP -> new Position(row - 1, column);
            case RIGHT -> new Position(row, column + 1);
            case DOWN -> new Position(row + 1, column);
            case LEFT -> new Position(row, column - 1);
        };
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
