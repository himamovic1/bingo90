package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CombinatorTest {

    @Test
    public void combineKOutOfN_2OutOf4() {
        // when
        var combinations = Combinator.combineKOutOfN(2, 4);

        // then
        assertThat(combinations).isEqualTo(Set.of(
                List.of(1, 2),
                List.of(2, 3),
                List.of(3, 4),
                List.of(1, 3),
                List.of(2, 4),
                List.of(1, 4)));
    }

    @Test
    public void combineKOutOfN_generateAllRowCombinations() {
        // when
        var combinations = Combinator.combineKOutOfN(4, 9);

        // then
        // as we need to pick 4 blank positions out of 9 possible
        // total number of those combinations is 9! / (4!*5!) == 126
        assertThat(combinations).hasSize(126);
    }

}
