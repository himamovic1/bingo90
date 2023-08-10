package org.example.rows;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShuffleRangeGenerator implements RangeGenerator {
    private final List<Integer> availableCoordinates;
    private final int rangeLength;

    public ShuffleRangeGenerator(int rangeLength, int maxLength) {
        this.rangeLength = rangeLength;
        this.availableCoordinates = IntStream.rangeClosed(0, maxLength - 1)
                .boxed().collect(Collectors.toList());
    }

    @Override
    public List<Integer> generateRange() {
        Collections.shuffle(availableCoordinates);
        return availableCoordinates.subList(0, this.rangeLength);
    }
}
