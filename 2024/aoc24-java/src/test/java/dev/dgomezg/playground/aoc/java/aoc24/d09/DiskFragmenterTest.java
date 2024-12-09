package dev.dgomezg.playground.aoc.java.aoc24.d09;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiskFragmenterTest {

    @Test
    void shouldDefragment() {
        DiskFragmenter diskFragmenter = new DiskFragmenter("12345");
        assertEquals("022111222......", diskFragmenter.deFragment());

        diskFragmenter = new DiskFragmenter("2333133121414131402");
        assertEquals("0099811188827773336446555566..............", diskFragmenter.deFragment());

    }

    @Test
    void shouldExpand() {
        DiskFragmenter diskFragmenter = new DiskFragmenter("12345");
        assertEquals("0..111....22222", diskFragmenter.expand());

        diskFragmenter = new DiskFragmenter("2333133121414131402");
        assertEquals("00...111...2...333.44.5555.6666.777.888899", diskFragmenter.expand());
    }

    @Test
    void shouldCalculateChecksum() {
        DiskFragmenter diskFragmenter = new DiskFragmenter("2333133121414131402");
        assertEquals(1928, diskFragmenter.checksum());
    }
}