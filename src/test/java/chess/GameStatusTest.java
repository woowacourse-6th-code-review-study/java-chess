package chess;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameStatusTest {

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
