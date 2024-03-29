package chess.controller.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.controller.State;
import chess.domain.game.ChessGame;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StatusTest {

    @DisplayName("명령어 입력이 status로만 이루어져 있으면 정상적으로 생성된다.")
    @Test
    void validateCommandInputSizeSuccessTest() {
        assertThatNoException()
                .isThrownBy(() -> new Status(List.of("status")));
    }

    @DisplayName("명령어 입력이 status로만 이루어져 있지 않으면 에러를 발생시킨다..")
    @Test
    void validateCommandInputSizeFailTest() {
        assertThatThrownBy(() -> new Status(List.of("status", " ")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("게임 점수 명령어 입력 형식이 올바르지 않습니다.");
    }

    @DisplayName("기능을 수행한 후 RUNNING 상태를 반환한다.")
    @Test
    void executeTest() {
        Status status = new Status(List.of("status"));
        ChessGame chessGame = new ChessGame();

        State gameState = status.execute(chessGame);

        assertThat(gameState).isEqualTo(State.RUNNING);
    }

}
