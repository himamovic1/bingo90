package org.example;

import org.example.rows.RangeGenerator;

import java.util.List;
import java.util.stream.IntStream;

public class BingoGenerator {
    public static final int TOTAL_NUMBER_OF_ROWS = 6 * 3;
    public static final int TOTAL_NUMBER_OF_COLS = 9;
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
    }

    private final RangeGenerator rangeGenerator;

    public BingoGenerator(RangeGenerator rangeGenerator) {
        this.rangeGenerator = rangeGenerator;
    }


    public int[][] generateBingo90Board() {
        int[][] board = null;

        while (board == null) {
            try {
                board = generateBlanksBoard();
            } catch (StackOverflowError e) {
                // ignore this error and ignore the log because it adds a lot of delay
                //System.out.println("Generating got stuck, retrying");
            }
        }

        return board;
    }

    public int[][] generateBlanksBoard() {
        var board = new int[TOTAL_NUMBER_OF_ROWS][TOTAL_NUMBER_OF_COLS];
        var blanksPerStripCol = new int[6][TOTAL_NUMBER_OF_COLS];
        var blanksPerCol = new int[TOTAL_NUMBER_OF_COLS];
        var iterationLimit = 150;

        // per strip
        for (int i = 0; i < TOTAL_NUMBER_OF_ROWS; i++) {
            int strip = i / 3;

            // pick one row variation from the list of possible ones
            var row = rangeGenerator.generateRange();
            while (!canUseRowVariation(blanksPerCol, blanksPerStripCol, row, i, strip)) {
                if (--iterationLimit <= 0) throw new StackOverflowError("Too many iterations");
                row = rangeGenerator.generateRange();
            }

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
