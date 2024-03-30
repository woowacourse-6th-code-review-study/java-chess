package chess.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameCommandTest {

    @Test
    @DisplayName("해당하는 커맨드를 찾는다.")
    void findCommandFromStringTest() {
        assertAll(
                () -> assertThat(GameCommand.from("start")).isEqualTo(GameCommand.START),
                () -> assertThat(GameCommand.from("end")).isEqualTo(GameCommand.END),
                () -> assertThat(GameCommand.from("move")).isEqualTo(GameCommand.MOVE),
                () -> assertThat(GameCommand.from("status")).isEqualTo(GameCommand.STATUS)
        );
    }

    @Test
    @DisplayName("해당하는 커맨드가 없으면 예외를 발생시킨다.")
    void findCommandFromStringTest_whenNotExist() {
        assertThatThrownBy(() -> GameCommand.from("notExistCommand"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 커멘드입니다.");
    }

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
