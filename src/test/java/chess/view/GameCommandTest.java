package chess.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameCommandTest {

    @Test
    @DisplayName("게임 시작 전 불가능한 커맨드인지 확인한다.")
    void isImpossibleCommandBeforeStartGameTest() {
        assertAll(
                () -> assertThat(GameCommand.isImpossibleBeforeStartGame(GameCommand.START)).isFalse(),
                () -> assertThat(GameCommand.isImpossibleBeforeStartGame(GameCommand.END)).isFalse(),
                () -> assertThat(GameCommand.isImpossibleBeforeStartGame(GameCommand.MOVE)).isTrue(),
                () -> assertThat(GameCommand.isImpossibleBeforeStartGame(GameCommand.STATUS)).isTrue()
        );
    }
}
