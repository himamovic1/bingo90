package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BingoGeneratorTest {
    public static final int TOTAL_NUMBER_OF_ROWS = 6 * 3;
    public static final int TOTAL_NUMBER_OF_COLS = 9;


    @Test
    public void generateBingo90_valid() {
        // when
        var board = BingoGenerator.generateBingo90Board();

        // then
        assertThat(board).hasDimensions(TOTAL_NUMBER_OF_ROWS, TOTAL_NUMBER_OF_COLS);
    }


    @Test
    public void getBlanksIndicesForColumn_valid() {
        // when
        var indices = BingoGenerator.getBlanksIndicesForColumn(0);

        // then
        assertThat(indices).hasSize(9);

        for (int i = 0; i < indices.length - 2; i++) {
            assertThat(indices[i]).isNotEqualTo(indices[i + 1] + 1);
            assertThat(indices[i + 1]).isNotEqualTo(indices[i + 2] + 2);
        }
    }
}
