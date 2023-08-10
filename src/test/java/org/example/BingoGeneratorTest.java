package org.example;

import org.example.rows.CombinatoricRangeGenerator;
import org.example.rows.RangeGenerator;
import org.example.rows.ShuffleRangeGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BingoGeneratorTest {
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
        assertIsValidBingo90Board(board);
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
        allBoards.forEach(this::assertIsValidBingo90Board);
    }

    @Test
    @Timeout(value = 10)
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
        assertIsValidBingo90Board(board);
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
        allBoards.forEach(this::assertIsValidBingo90Board);
    }

    @Test
    @Timeout(value = 10)
    public void generateBingo90__combinator_10kTickets_valid() {
        // given
        var sut = new BingoGenerator(COMBINATOR_RANGE_GENERATOR);

        // when
        for (int i = 0; i < 10000; i++)
            sut.generateBingo90Board();

        // then
        // test should complete before the timeout
    }


    private void assertIsValidBingo90Board(int[][] board) {
        assertThat(board).hasDimensions(18, 9);

        // check it has exactly four blanks per row
        // and correct number of blanks per column
        var blanksPerRow = new int[18];
        var blanksPerCol = new int[9];
        for (int row = 0; row < 18; row++)
            for (int col = 0; col < 9; col++)
                if (board[row][col] == -1) {
                    blanksPerRow[row]++;
                    blanksPerCol[col]++;
                }

        assertThat(Arrays.stream(blanksPerRow).boxed().toList())
                .isEqualTo(Collections.nCopies(18, 4));

        assertThat(Arrays.stream(blanksPerCol).boxed().toList())
                .isEqualTo(List.of(9, 8, 8, 8, 8, 8, 8, 8, 7));
    }
}
