package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {
    @DisplayName("점수끼리 덧셈을 계산할 수 있다")
    @Test
    void should_CalculateAddition() {
        Score score1 = new Score(1.1);
        Score score2 = new Score(1.2);
        Score added = score1.add(score2);

        assertThat(added.getValue()).isEqualTo(2.3);
    }

    @DisplayName("점수끼리 곱셈을 계산할 수 있다")
    @Test
    void should_CalculateMultiplication() {
        Score score1 = new Score(1.1);
        Score score2 = new Score(1.2);
        Score multiplied = score1.multiply(score2);

        assertThat(multiplied.getValue()).isEqualTo(1.32);
    }

    @DisplayName("특정 점수가 더 높은지 계산할 수 있다")
    @Test
    void should_ScoreCouldCheckIsAbove_When_OtherScoreIsGiven() {
        Score higher = new Score(1.3);
        Score lower = new Score(1.2);

        assertThat(higher.isAbove(lower)).isTrue();
    }

    @DisplayName("특정 점수가 더 낮은지 계산할 수 있다")
    @Test
    void should_ScoreCouldCheckIsBelow_When_OtherScoreIsGiven() {
        Score higher = new Score(1.3);
        Score lower = new Score(1.2);

        assertThat(lower.isBelow(higher)).isTrue();
    }
}
