package chess;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameStatusTest {

    @Test
    @DisplayName("이긴 팀으로 해당 상태를 판단한다.")
    void winByWhichTeamTest() {
        assertAll(
                () -> assertThat(GameStatus.winBy(Team.BLACK)).isEqualTo(GameStatus.BLACK_WIN),
                () -> assertThat(GameStatus.winBy(Team.WHITE)).isEqualTo(GameStatus.WHITE_WIN)
        );
    }

    @Test
    @DisplayName("플레이 중인 상태인지 확인한다.")
    void isPlayingTest() {
        assertAll(
                () -> assertThat(GameStatus.isPlaying(GameStatus.PLAYING)).isTrue(),
                () -> assertThat(GameStatus.isPlaying(GameStatus.END)).isFalse(),
                () -> assertThat(GameStatus.isPlaying(GameStatus.BLACK_WIN)).isFalse(),
                () -> assertThat(GameStatus.isPlaying(GameStatus.WHITE_WIN)).isFalse()
        );
    }
}