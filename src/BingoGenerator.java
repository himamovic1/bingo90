import java.util.List;
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

    public static int[][] generateBingo90Board() {
        var board = new int[TOTAL_NUMBER_OF_ROWS][TOTAL_NUMBER_OF_COLS];
        return board;
    }
}
