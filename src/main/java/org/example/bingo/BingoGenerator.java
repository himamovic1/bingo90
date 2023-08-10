package org.example.bingo;

import org.example.exception.TooManyIterationsException;
import org.example.random.RangeGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BingoGenerator {
    public static final int TOTAL_NUMBER_OF_ROWS = 6 * 3;
    public static final int TOTAL_NUMBER_OF_COLS = 9;
    public static final int ITERATION_LIMIT = 500;
    public static final int BLANK = -1;
    private static final List<List<Integer>> NUMS_PER_COL;
    private static final List<Integer> BLANKS_PER_COL;

    static {
        NUMS_PER_COL = List.of(
                IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toList()),
                IntStream.rangeClosed(10, 19).boxed().collect(Collectors.toList()),
                IntStream.rangeClosed(20, 29).boxed().collect(Collectors.toList()),
                IntStream.rangeClosed(30, 39).boxed().collect(Collectors.toList()),
                IntStream.rangeClosed(40, 49).boxed().collect(Collectors.toList()),
                IntStream.rangeClosed(50, 59).boxed().collect(Collectors.toList()),
                IntStream.rangeClosed(60, 69).boxed().collect(Collectors.toList()),
                IntStream.rangeClosed(70, 79).boxed().collect(Collectors.toList()),
                IntStream.rangeClosed(80, 90).boxed().collect(Collectors.toList()));

        BLANKS_PER_COL = NUMS_PER_COL.stream()
                .map(c -> TOTAL_NUMBER_OF_ROWS - c.size())
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
                return populateWithNumbers(board);
            } catch (TooManyIterationsException e) {
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

        // per strip
        for (int i = 0; i < TOTAL_NUMBER_OF_ROWS; i++) {
            var iterationLimit = ITERATION_LIMIT;
            int strip = i / 3;

            // pick one row variation from the list of possible ones
            var row = rangeGenerator.generateRange();
            while (!canUseRowVariation(blanksPerCol, blanksPerStripCol, row, i, strip)) {
                if (--iterationLimit <= 0) throw new TooManyIterationsException();
                row = rangeGenerator.generateRange();
            }

            for (var col : row) {
                board[i][col] = BLANK; // set blank field
                blanksPerCol[col]++; // increment column counter
                blanksPerStripCol[strip][col]++; // increment column per strip counter
            }
        }

        return board;
    }

    private static boolean canUseRowVariation(
            int[] blanksPerCol, int[][] blanksPerStripCol, List<Integer> rowVariation, int rowIndex, int stripIndex) {

        var state = blanksPerCol.clone();
        var potentialBlankPlacesLeft = TOTAL_NUMBER_OF_ROWS;

        for (var col : rowVariation) {
            if (blanksPerCol[col] >= BLANKS_PER_COL.get(col)) return false;
            if (blanksPerStripCol[stripIndex][col] >= 2) return false;
            state[col]++;
        }

        // check if there will be enough places for blanks per column after this row is added
        potentialBlankPlacesLeft = (6 - stripIndex - 1) * 2 + 2 - (rowIndex % 3);
        for (int col = 0; col < TOTAL_NUMBER_OF_COLS; col++)
            if (BLANKS_PER_COL.get(col) - state[col] > potentialBlankPlacesLeft)
                return false;

        return true;
    }

    private static int[][] populateWithNumbers(int[][] board) {
        for (int col = 0; col < TOTAL_NUMBER_OF_COLS; col++) {
            Collections.shuffle(NUMS_PER_COL.get(col));

            // create list of numbers for column
            var iter = NUMS_PER_COL.get(col).iterator();
            var column = new ArrayList<Integer>(TOTAL_NUMBER_OF_ROWS);
            for (int row = 0; row < TOTAL_NUMBER_OF_ROWS; row++)
                column.add(board[row][col] != BLANK ? iter.next() : BLANK);

            // ensure numbers within a strip are sorted desc
            for (int row = 0; row < TOTAL_NUMBER_OF_ROWS; row += 3) {
                var numbers = column.subList(row, row + 3).stream()
                        .filter(n -> n != BLANK).sorted().iterator();

                for (int i = row; i < row + 3; i++)
                    if (board[i][col] != BLANK)
                        board[i][col] = numbers.next();
            }
        }

        return board;
    }
}
