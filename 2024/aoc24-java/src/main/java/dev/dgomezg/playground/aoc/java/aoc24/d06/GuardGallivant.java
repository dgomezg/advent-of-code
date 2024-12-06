package dev.dgomezg.playground.aoc.java.aoc24.d06;


import dev.dgomezg.playground.aoc.java.utils.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

public class GuardGallivant {

    public static boolean UPDATE_N_PRINT_TRACK_IN_MAP = false;
    private static char[][] map;

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> fileLines = FileUtils.getFileLines("d06-GuardGallivant/d06-dgomezg.txt");
        Guard guard = null;

        map = new char[fileLines.size()][];

        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            map[i] = line.toCharArray();
            if (guard == null) {
                guard = checkGuard(line, i);
            }
        }
        System.out.println("Guard: " + guard);
        Position startingPosition = guard.getPosition();
        Direction startingDirection = guard.getDirection();
        guard.patrol(map);

        System.out.println("Part 01 +" + guard.getNumOfVisitedPositions());

        //Part02: try to set an obstacle in each of the visited positions to see if the guard enters a loop.
        Set<Position> visitedPositions = guard.getVisitedPositions();
        visitedPositions.remove(startingPosition);
        System.out.printf("Trying to set obstacles in %d positions to provoke a loop%n", visitedPositions.size());
        long loopsDetected = 0L;
        long tries = 0L;
        for (Position position : visitedPositions) {
            tries++;
            System.out.printf("Obstacle %5d/%5d  set at %s. The guard starts patrol.", tries, visitedPositions.size(), position);
            //set the obstacle
            map[position.row()][position.column()] = '#';
            Guard newGuard = new Guard(startingPosition, startingDirection);
            if (!newGuard.patrol(map)) {
                loopsDetected++;
                System.out.printf("\t Guard is in a loop. Loops detected %d%n", loopsDetected);
            } else {
                System.out.printf("\t Guard exited the map%n");
            }
            map[position.row()][position.column()] = '.';
        }

        System.out.printf("Part 02 : %d", loopsDetected);
    }

    private static Guard checkGuard(String line, int lineNumber) {
        for (Direction direction : Direction.values()) {
            Guard result = null;
            result = checkGuard(line, lineNumber, direction);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static Guard checkGuard(String line, int lineNumber, Direction direction) {
        if (line.indexOf(direction.value) > 0) {
            return new Guard(new Position(lineNumber, line.indexOf(direction.value)), direction);
        }
        return null;
    }


}
