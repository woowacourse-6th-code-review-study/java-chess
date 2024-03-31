package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @DisplayName("두개의 score를 더한다.")
    @Test
    void add() {
        final Score one = new Score(1);
        final Score two = new Score(2);

        final Score expected = new Score(3);

        assertThat(one.plus(two)).isEqualTo(expected);
    }

    @DisplayName("두개의 score를 뺸다.")
    @Test
    void minus() {
        final Score three = new Score(3);
        final Score two = new Score(2);

        final Score expected = new Score(1);

        assertThat(three.minus(two)).isEqualTo(expected);
    }
}
