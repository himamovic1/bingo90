import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BingoGenerator {
    public static final int TOTAL_NUMBER_OF_ROWS = 6 * 3;
    public static final int TOTAL_NUMBER_OF_COLS = 9;
    private static final List<int[]> NUMS_PER_COL = List.of(
            IntStream.rangeClosed(1, 9).toArray(),
            IntStream.rangeClosed(10, 19).toArray(),
            IntStream.rangeClosed(20, 29).toArray(),
            IntStream.rangeClosed(30, 39).toArray(),
            IntStream.rangeClosed(40, 49).toArray(),
            IntStream.rangeClosed(50, 59).toArray(),
            IntStream.rangeClosed(60, 69).toArray(),
            IntStream.rangeClosed(70, 79).toArray(),
            IntStream.rangeClosed(80, 90).toArray());
    private static final List<List<Integer>> TRIPLETS = List.of(
            List.of(0, 1, 2), List.of(3, 4, 5), List.of(6, 7, 8),
            List.of(9, 10, 11), List.of(12, 13, 14), List.of(15, 16, 17));


    public static int[][] generateBingo90Board() {
        var board = new int[TOTAL_NUMBER_OF_ROWS][TOTAL_NUMBER_OF_COLS];

        for (int col = 0; col < TOTAL_NUMBER_OF_COLS; col++) {
            var blankIndices = getBlanksIndicesForColumn(col);

            // put blanks in the board column
            for (int row : blankIndices)
                board[row][col] = 8;
        }

        return board;
    }


    /**
     * generate a list on indices representing where blanks will be in a single column
     */
    private static int[] getBlanksIndicesForColumn(int columnIndex) {
        var numOfBlanks = TOTAL_NUMBER_OF_ROWS - NUMS_PER_COL.get(columnIndex).length;
        var positions = IntStream.rangeClosed(0, TOTAL_NUMBER_OF_ROWS - 1).boxed().collect(Collectors.toList());
        var indices = new int[numOfBlanks];

        while (true) {
            // get needed number of positions for the given column
            Collections.shuffle(positions);
            var sub = positions.subList(0, numOfBlanks);
            Collections.sort(sub);

            // cannot have 3 blanks in one column in a strip
            if (Utils.containsAnySublist(sub, TRIPLETS))
                continue;

            //System.out.printf("Indices are [%s] %n", Arrays.toString(indices));
            for (int i = 0; i < numOfBlanks; i++) indices[i] = sub.get(i);
            return indices;
        }
    }
}
