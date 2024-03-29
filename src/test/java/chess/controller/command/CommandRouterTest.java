package chess.controller.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommandRouterTest {

    @DisplayName("게임 시작 커맨드를 반환한다.")
    @Test
    void findStartCommendByInputTest() {
        Command start = CommandRouter.findCommendByInput(List.of("start"));

        assertThat(start).isInstanceOf(Start.class);
    }

    @DisplayName("게임 이동 커맨드를 반환한다.")
    @Test
    void findMoveCommendByInputTest() {
        Command start = CommandRouter.findCommendByInput(List.of("move", "b2", "b4"));

        assertThat(start).isInstanceOf(Move.class);
    }

    @DisplayName("게임 종료 커맨드를 반환한다.")
    @Test
    void findEndCommendByInputTest() {
        Command start = CommandRouter.findCommendByInput(List.of("end"));

        assertThat(start).isInstanceOf(End.class);
    }

    @DisplayName("게임 점수 커맨드를 반환한다.")
    @Test
    void findStatusCommendByInputTest() {
        Command start = CommandRouter.findCommendByInput(List.of("status"));

        assertThat(start).isInstanceOf(Status.class);
    }

    @DisplayName("커맨드 입력 리스트 크기가 0이면 에러를 발생시킨다.")
    @Test
    void commendInputFormatSizeTest() {
        assertThatThrownBy(() -> CommandRouter.findCommendByInput(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 값 입력을 허용하지 않습니다.");
    }

    @DisplayName("입력과 일치하는 커맨드를 찾지 못하면 에러를 발생시킨다.")
    @Test
    void findCommendByInputFailTest() {
        assertThatThrownBy(() -> CommandRouter.findCommendByInput(List.of("jazz")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("옳바르지 않은 명령어 입력입니다.");
    }
}
