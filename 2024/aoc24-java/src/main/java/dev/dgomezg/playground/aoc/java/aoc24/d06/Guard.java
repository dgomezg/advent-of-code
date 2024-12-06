package dev.dgomezg.playground.aoc.java.aoc24.d06;

import java.util.HashSet;
import java.util.Set;

import static dev.dgomezg.playground.aoc.java.aoc24.d06.GuardGallivant.UPDATE_N_PRINT_TRACK_IN_MAP;

public  class Guard {
    private Position position;
    private Direction direction;
    Set<Position> visited = new HashSet<>();
    Set<VisitedObstacle> visitedObstacles = new HashSet<>();
    boolean inLoop = false;

    public Guard(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
        visited.add(position);
    }

    /**
     * @return true if the guard has exited the map
     * false if the guard is in a loop
     */
    boolean patrol(char[][] map) {
        while (!hasExitedMap(map) && !inLoop) {
            this.move(map);
            if (UPDATE_N_PRINT_TRACK_IN_MAP) {
                printMap(map);
                System.out.println(this);
            }
        }
        return hasExitedMap(map);
    }

    boolean canMove(char[][] map) {
        switch (direction) {
            case UP:
                return map[position.row() - 1][position.column()] != '#';
            case RIGHT:
                return map[position.row()][position.column() + 1] != '#';
            case DOWN:
                return map[position.row() + 1][position.column()] != '#';
            case LEFT:
                return map[position.row()][position.column() - 1] != '#';
        }
        return false;
    }

    public boolean isInLoop() {
        return inLoop;
    }

    public boolean hasExitedMap(char[][] map) {
        switch (this.direction) {
            case LEFT:
                return this.position.column() == 0;
            case RIGHT:
                return this.position.column() == map[0].length - 1;
            case UP:
                return this.position.row() == 0;
            case DOWN:
                return this.position.row() == map.length - 1;
        }
        return false;
    }

    void move(char[][] map) {
        if (!canMove(map)) {
            var visitedObstacle = new VisitedObstacle(position.move(direction), direction);
            if (visitedObstacles.contains(visitedObstacle)) {
                inLoop = true;
            }
            visitedObstacles.add(visitedObstacle);
            turnRight();
        } else {
            if (UPDATE_N_PRINT_TRACK_IN_MAP) {
                map[position.row()][position.column()] = 'X';
            }
            this.position = this.position.move(direction);
            this.visited.add(this.position);
        }
        if (UPDATE_N_PRINT_TRACK_IN_MAP) {
            map[this.position.row()][this.position.column()] = direction.value;
        }
    }

    public long getNumOfVisitedPositions() {
        return visited.size();
    }

    public Set<Position> getVisitedPositions() {
        return visited;
    }

    private void turnRight() {
        Direction[] directions = Direction.values();
        this.direction = directions[(direction.ordinal() + 1) % directions.length];
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Guard{" +
                "position=" + position +
                ", direction=" + direction +
                '}';
    }

    private static void printMap(char[][] map) {
        System.out.println("------------------------");
        for (int row = 0; row < map.length; row++) {
            System.out.println(new String(map[row]));
        }
    }

}
