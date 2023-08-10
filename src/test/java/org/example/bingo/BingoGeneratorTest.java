package org.example.bingo;

import org.example.random.CombinatoricRangeGenerator;
import org.example.random.RangeGenerator;
import org.example.random.ShuffleRangeGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BingoGeneratorTest {
    private static final int TOTAL_COLS = 9;
    private static final int TOTAL_ROWS = 18;
    private static final int BLANK = -1;

    private static final RangeGenerator SHUFFLE_RANGE_GENERATOR =
            new ShuffleRangeGenerator(4, 9);

    private static final RangeGenerator COMBINATOR_RANGE_GENERATOR =
            new CombinatoricRangeGenerator(4, 9);


    @Test
    public void generateBingo90_shuffle_single_valid() {
        // given
        var sut = new BingoGenerator(SHUFFLE_RANGE_GENERATOR);

        // when
        var board = sut.generateBingo90Board();

        // then
        assertBoardIsComplete(board);
        assertStripColumnsInOrder(board);
    }

    @Test
    public void generateBingo90_shuffle_hundredTickets_valid() {
        // given
        var sut = new BingoGenerator(SHUFFLE_RANGE_GENERATOR);
        var allBoards = new LinkedList<int[][]>();

        // when
        for (int i = 0; i < 100; i++)
            allBoards.add(sut.generateBingo90Board());

        // then
        assertThat(allBoards).allSatisfy(this::assertBoardIsComplete);
        assertThat(allBoards).allSatisfy(this::assertStripColumnsInOrder);
    }

    @Test
    @Timeout(value = 1)
    public void generateBingo90__shuffle_10kTickets_valid() {
        // given
        var sut = new BingoGenerator(SHUFFLE_RANGE_GENERATOR);

        // when
        for (int i = 0; i < 10000; i++)
            sut.generateBingo90Board();

        // then
        // test should complete before the timeout
    }


    @Test
    public void generateBingo90_combinator_single_valid() {
        // given
        var sut = new BingoGenerator(COMBINATOR_RANGE_GENERATOR);

        // when
        var board = sut.generateBingo90Board();

        // then
        assertBoardIsComplete(board);
        assertStripColumnsInOrder(board);
    }

    @Test
    public void generateBingo90_combinator_hundredTickets_valid() {
        // given
        var sut = new BingoGenerator(COMBINATOR_RANGE_GENERATOR);
        var allBoards = new LinkedList<int[][]>();

        // when
        for (int i = 0; i < 100; i++)
            allBoards.add(sut.generateBingo90Board());

        // then
        assertThat(allBoards).allSatisfy(this::assertBoardIsComplete);
        assertThat(allBoards).allSatisfy(this::assertStripColumnsInOrder);
    }

    @Test
    @Timeout(value = 1)
    public void generateBingo90__combinator_10kTickets_valid() {
        // given
        var sut = new BingoGenerator(COMBINATOR_RANGE_GENERATOR);

        // when
        for (int i = 0; i < 10000; i++)
            sut.generateBingo90Board();

        // then
        // test should complete before the timeout
    }


    private void assertBoardIsComplete(int[][] board) {
        assertThat(board).hasDimensions(18, 9);

        var blanksPerRow = new int[TOTAL_ROWS];
        var blanksPerCol = new int[TOTAL_COLS];
        var numbers = new HashSet<Integer>();

        for (int row = 0; row < TOTAL_ROWS; row++)
            for (int col = 0; col < TOTAL_COLS; col++)
                if (board[row][col] == BLANK) {
                    // check it has exactly four blanks per row
                    // and correct number of blanks per column
                    blanksPerRow[row]++;
                    blanksPerCol[col]++;
                } else {
                    // to confirm it contains all numbers between 1 and 90
                    numbers.add(board[row][col]);
                }

        // assert it contains all numbers which is guaranteed for 6 strip bingo 90
        assertThat(numbers).hasSize(90);
        assertThat(Collections.min(numbers)).isEqualTo(1);
        assertThat(Collections.max(numbers)).isEqualTo(90);

        // assert each row has exactly 4 blank spaces
        assertThat(Arrays.stream(blanksPerRow).boxed().toList())
                .isEqualTo(Collections.nCopies(18, 4));

        // assert each column has exactly needed number of blanks
        assertThat(Arrays.stream(blanksPerCol).boxed().toList())
                .isEqualTo(List.of(9, 8, 8, 8, 8, 8, 8, 8, 7));
    }

    private void assertStripColumnsInOrder(int[][] board) {
        for (int col = 0; col < TOTAL_COLS; col++) {
            // check 3 rows per strip
            for (int row = 0; row < TOTAL_ROWS; row += 3) {
                var values = Stream.of(board[row][col], board[row + 1][col], board[row + 2][col])
                        .filter(v -> v != BLANK).toList();

                assertThat(values).isSorted();
            }
        }
    }
}
