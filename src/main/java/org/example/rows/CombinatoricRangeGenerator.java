package org.example.rows;

import org.example.Combinator;

import java.util.List;
import java.util.Random;

public class CombinatoricRangeGenerator implements RangeGenerator {
    private final List<List<Integer>> rowVariations;
    private final Random randomGenerator;

    public CombinatoricRangeGenerator(int rangeLength, int maxLength) {
        rowVariations = Combinator.combineKOutOfN(rangeLength, maxLength);
        randomGenerator = new Random();
    }

    @Override
    public List<Integer> generateRange() {
        return rowVariations.get(randomGenerator.nextInt(rowVariations.size()));
    }
}
