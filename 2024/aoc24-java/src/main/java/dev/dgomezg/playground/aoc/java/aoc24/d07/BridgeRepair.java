package dev.dgomezg.playground.aoc.java.aoc24.d07;


import dev.dgomezg.playground.aoc.java.utils.FileUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class BridgeRepair {

    public static final String FILENAME = "d07-BridgeRepair/d07-dgomezg.txt";

    public static void main(String[] args) throws IOException, URISyntaxException {
        long result = FileUtils.getLines(FILENAME)
                .map(TestValue::new)
                .filter(TestValue::isValidPart01)
                .mapToLong(TestValue::getResult)
                .sum();

        // x 24307870799 -> is too low
        // 267566105056
        System.out.println("Part01 : " + result);

        long result02 = FileUtils.getLines(FILENAME)
                .map(TestValue::new)
                .filter(TestValue::isValidPart02)
                .mapToLong(TestValue::getResult)
                .sum();

        //116094961956019
        System.out.println("Part02 : " + result02);
    }


}
