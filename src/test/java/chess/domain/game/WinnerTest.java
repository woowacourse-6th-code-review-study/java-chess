package chess.domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.piece.Color;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class WinnerTest {

    @Nested
    @DisplayName("점수로 승리를 결정한다.")
    class SelectWinnerByScoreTest {

        @DisplayName("흰색 팀의 점수가 검정 팀보다 높으면 흰색 팀이 승리한다.")
        @Test
        void whiteTeamScoreIsGreaterThanBlackTeam() {
            Map<Color, Score> map = Map.of(Color.WHITE, new Score(33.0), Color.BLACK, new Score(27.0));

            Winner winner = Winner.selectWinnerByScore(map);

            assertThat(winner).isEqualTo(Winner.WHITE_WIN);
        }

        @DisplayName("검정 팀의 점수가 흰색 팀보다 높으면 검정 팀이 승리한다.")
        @Test
        void blackTeamScoreIsGreaterThanWhiteTeam() {
            Map<Color, Score> map = Map.of(Color.WHITE, new Score(26.9), Color.BLACK, new Score(27.0));

            Winner winner = Winner.selectWinnerByScore(map);

            assertThat(winner).isEqualTo(Winner.BLACK_WIN);
        }

        @DisplayName("검정 팀의 점수가 흰색 팀과 같으면 무승부")
        @Test
        void blackTeamScoreEqualWhiteTeam() {
            Map<Color, Score> map = Map.of(Color.WHITE, new Score(27.0), Color.BLACK, new Score(27.0));

            Winner winner = Winner.selectWinnerByScore(map);

            assertThat(winner).isEqualTo(Winner.DRAW);
        }
    }

    @Nested
    @DisplayName("체크메이트로 승리를 결정한다")
    class SelectWinnerByCheckmateTest {

        @DisplayName("검정 색의 King이 체크메이트를 당하면 흰색 팀이 승리한다.")
        @Test
        void blackKingCheckmateTest() {
            Winner winner = Winner.selectWinnerByCheckmate(Color.BLACK);

            assertThat(winner).isEqualTo(Winner.WHITE_WIN);
        }

        @DisplayName("흰색 색의 King이 체크메이트를 당하면 검정 팀이 승리한다.")
        @Test
        void whiteKingCheckmateTest() {
            Winner winner = Winner.selectWinnerByCheckmate(Color.WHITE);

            assertThat(winner).isEqualTo(Winner.BLACK_WIN);
        }
    }
}
