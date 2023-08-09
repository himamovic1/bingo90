package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class BingoGeneratorTest {

    @Test
    public void generateBingo90_valid() {
        // when
        var board = BingoGenerator.generateBingo90Board();

        // then
        assertThat(board).hasDimensions(18, 9);

        // check it has exactly four blanks per row
        var blanksPerRow = new int[18];
        for (int row = 0; row < 18; row++)
            for (int col = 0; col < 9; col++)
                if (board[row][col] == -1)
                    blanksPerRow[row]++;

        assertThat(Arrays.stream(blanksPerRow).boxed().toList())
                .isEqualTo(Collections.nCopies(18, 4));
    }


    @Test
    public void getBlanksIndicesForColumn_valid() {
        // when
        var indices = BingoGenerator.getBlanksIndicesForColumn(0);

        // then
        assertThat(indices).hasSize(9);

        for (int i = 0; i < indices.size() - 2; i++) {
            assertThat(indices.get(i)).isNotEqualTo(indices.get(i + 1) + 1);
            assertThat(indices.get(i + 1)).isNotEqualTo(indices.get(i + 2) + 2);
        }
    }
}
