package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @Test
    @DisplayName("두개의 score를 더한다.")
    void add() {
        final Score one = new Score(1);
        final Score two = new Score(2);

        final Score expected = new Score(3);

        assertThat(one.add(two)).isEqualTo(expected);
    }
}
