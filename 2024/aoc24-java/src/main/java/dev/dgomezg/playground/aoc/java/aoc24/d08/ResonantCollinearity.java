package dev.dgomezg.playground.aoc.java.aoc24.d08;

import dev.dgomezg.playground.aoc.java.utils.FileUtils;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class ResonantCollinearity {

    public static boolean isAntenna(char item) {
        return (item >= 'A' && item <= 'Z') || (item >= 'a' && item <= 'z') || (item >= '0' && item <= '9');
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> fileLines = FileUtils.getFileLines("d08-ResonantCollinearity/d08-dgomezg.txt");

        char[][] map = new char[fileLines.size()][];
        HashMap<Character, List<Position>> antennas = new HashMap<>();


        int row = 0;
        for (String line : fileLines) {
            map[row] = line.toCharArray();
            int col = 0;
            for (char c : map[row]) {
                if (isAntenna(c)) {
                    if (!antennas.containsKey(c)) {
                        antennas.put(c, new ArrayList<>());
                    }
                    antennas.get(c).add(new Position(row, col));
                }
                col++;
            }
            row++;
        }
        printMap(map);

        Collection<MapLayer> antennasMap = antenasToMapLayer(antennas);

        Set<Position> antinodes = new HashSet<>();
        System.out.printf("There are %d different type of antennas in the map%n", antennas.size());
        for (Map.Entry<Character, List<Position>> antennaFamily : antennas.entrySet()) {
            List<Position> antennaInstallations = antennaFamily.getValue();
            System.out.printf("\tAntena %s has %d installations%n", antennaFamily.getKey(), antennaInstallations.size());

            int currAntennaIdx = 0;
            for (Position currAntenna : antennaInstallations) {
                //find distance with the rest of antennas
                for (int i = currAntennaIdx + 1; i < antennaInstallations.size(); i++) {
                    System.out.printf("\tComparing Antena %s with %s.", currAntenna, antennaInstallations.get(i));
                    Position distance = currAntenna.distanceTo(antennaInstallations.get(i));
                    System.out.printf("\tDistance between Antennas is %s%n", distance);
                    Position antinodeA = antennaInstallations.get(i).moveTo(distance);
                    Position antinodeB = currAntenna.moveTo(distance.reverse());
                    antinodes.add(antinodeA);
                    antinodes.add(antinodeB);
                    System.out.printf("\t\tAdded antinodes %s and %s%n", antinodeA, antinodeB);
                }
                currAntennaIdx++;

            }
            /*
                generateAndPrintMap(map.length, map[0].length,
                        List.of(new MapLayer(antennaFamily.getKey(), antennaFamily.getValue()),
                                new MapLayer('#', antinodes)));
*/
        }

        long count = antinodes.stream()
                .filter(a -> a.isInsideMap(map))
                .count();

        Collection<MapLayer> mapLayers = new ArrayList<>();
        mapLayers.add(new MapLayer('#', antinodes));
        mapLayers.addAll(antenasToMapLayer(antennas));
        generateAndPrintMap(map.length, map[0].length, mapLayers);
        System.out.println();
        generateAndPrintMap(map.length, map[0].length, List.of(new MapLayer('#', antinodes)));

        System.out.printf("Part01: %d%n", count);


        Set<Position> antinodesPart02 = new HashSet<>();
        System.out.printf("There are %d different type of antennas in the map%n", antennas.size());
        for (Map.Entry<Character, List<Position>> antennaFamily : antennas.entrySet()) {
            List<Position> antennaInstallations = antennaFamily.getValue();
            System.out.printf("\tAntena %s has %d installations%n", antennaFamily.getKey(), antennaInstallations.size());

            int currAntennaIdx = 0;
            for (Position currAntenna : antennaInstallations) {
                //find distance with the rest of antennas
                for (int i = currAntennaIdx + 1; i < antennaInstallations.size(); i++) {
                    System.out.printf("\tComparing Antena %s with %s.", currAntenna, antennaInstallations.get(i));
                    Position distance = currAntenna.distanceTo(antennaInstallations.get(i));
                    System.out.printf("\tDistance between Antennas is %s%n", distance);

                    System.out.printf("\t\tAntinodes in positive direction: ");
                    //Position antinodeA = antennaInstallations.get(i).moveTo(distance);
                    Position antinodeA = currAntenna.moveTo(distance);
                    while (antinodeA.isInsideMap(map)) {
                        System.out.printf("%s - ", antinodeA);
                        antinodesPart02.add(antinodeA);
                        antinodeA = antinodeA.moveTo(distance);
                    }

                    System.out.printf("\t\tAntinodes in reverse direction: ");
                    //Position antinodeB = currAntenna.moveTo(distance.reverse());
                    Position antinodeB = antennaInstallations.get(i).moveTo(distance.reverse());
                    while (antinodeB.isInsideMap(map)) {
                        System.out.printf("%s - ", antinodeB);
                        antinodesPart02.add(antinodeB);
                        antinodeB = antinodeB.moveTo(distance.reverse());
                    }
                    System.out.println();
                }
                currAntennaIdx++;

            }
        }

        long countPar02 = antinodesPart02.stream()
                .filter(a -> a.isInsideMap(map))
                .count();


        System.out.printf("part02: %d%n", countPar02);
    }

    public static void generateAndPrintMap(int height, int width, Collection<MapLayer> layers) {
        char[][] map = new char[height][];
        for (int row = 0; row < height; row++) {
            map[row] = new char[width];
            Arrays.fill(map[row], '.');
        }

        for(MapLayer layer : layers) {
            for(Position position : layer.getPositions()) {
                if (position.isInsideMap(map)) {
                    map[position.row()][position.column()] = layer.getSymbol().charValue();
                }
            }
        }
        printMap(map);
    }

    private static void printMap(char[][] map) {
        int width = map[0].length;
        for (int row = 0; row < width; row++) {
            System.out.println(new String(map[row]));
        }
    }

    public static Collection<MapLayer> antenasToMapLayer(HashMap<Character, List<Position>> antennas) {

        return antennas.entrySet().stream()
                .map(entry -> new MapLayer(entry.getKey(), entry.getValue()))
                .toList();
    }
}


