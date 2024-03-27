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
}
