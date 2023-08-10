package org.example;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class BingoGenerator {
    public static final int TOTAL_NUMBER_OF_ROWS = 6 * 3;
    public static final int TOTAL_NUMBER_OF_COLS = 9;

    private static final List<List<Integer>> ROW_BLANKS_VARIATIONS;
    private static final List<int[]> NUMS_PER_COL;
    private static final List<Integer> BLANKS_PER_COL;

    static {
        NUMS_PER_COL = List.of(
                IntStream.rangeClosed(1, 9).toArray(),
                IntStream.rangeClosed(10, 19).toArray(),
                IntStream.rangeClosed(20, 29).toArray(),
                IntStream.rangeClosed(30, 39).toArray(),
                IntStream.rangeClosed(40, 49).toArray(),
                IntStream.rangeClosed(50, 59).toArray(),
                IntStream.rangeClosed(60, 69).toArray(),
                IntStream.rangeClosed(70, 79).toArray(),
                IntStream.rangeClosed(80, 90).toArray());

        BLANKS_PER_COL = NUMS_PER_COL.stream()
                .map(c -> TOTAL_NUMBER_OF_ROWS - c.length)
                .toList();

        ROW_BLANKS_VARIATIONS = Combinator.combineKOutOfN(4, 9);
    }


    public static int[][] generateBingo90Board() {
        System.out.println("Generating a new bingo board");
        return generateBlanksBoard();
    }

    public static int[][] generateBlanksBoard() {
        var board = new int[TOTAL_NUMBER_OF_ROWS][TOTAL_NUMBER_OF_COLS];
        var blanksPerStripCol = new int[6][TOTAL_NUMBER_OF_COLS];
        var blanksPerCol = new int[TOTAL_NUMBER_OF_COLS];
        var rndGenerator = new Random();

        // per strip
        for (int i = 0; i < TOTAL_NUMBER_OF_ROWS; i++) {
            int strip = i / 3;

            // pick one row variation from the list of possible ones
            var row = ROW_BLANKS_VARIATIONS.get(rndGenerator.nextInt(ROW_BLANKS_VARIATIONS.size()));
            while (!canUseRowVariation(blanksPerCol, blanksPerStripCol, row, i, strip))
                row = ROW_BLANKS_VARIATIONS.get(rndGenerator.nextInt(ROW_BLANKS_VARIATIONS.size()));

            for (var col : row) {
                board[i][col] = -1; // set blank field
                blanksPerCol[col]++; // increment column counter
                blanksPerStripCol[strip][col]++; // increment column per strip counter
            }
        }

        return board;
    }

    private static boolean canUseRowVariation(
            int[] blanksPerCol, int[][] blanksPerStripCol, List<Integer> rowVariation, int rowIndex, int stripIndex) {

        var state = blanksPerCol.clone();

        for (var col : rowVariation) {
            if (blanksPerCol[col] >= BLANKS_PER_COL.get(col)) return false;
            if (blanksPerStripCol[stripIndex][col] >= 2) return false;
            state[col]++;
        }

        for (int col = 0; col < TOTAL_NUMBER_OF_COLS; col++)
            if (BLANKS_PER_COL.get(col) - state[col] > ((6 - stripIndex - 1) * 2 + (2 - rowIndex % 3)))
                return false;

        return true;
    }
}
