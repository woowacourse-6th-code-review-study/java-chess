package chess.controller.command;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoveTest {

    @DisplayName("명령어 입력 형식이 올바르면 정상적으로 생성된다.")
    @Test
    void validateCommandInputSizeSuccessTest() {
        assertThatNoException()
                .isThrownBy(() -> new Move(List.of("move", "c2", "c3")));
    }

    @DisplayName("명령어 입력이 올바르지 않으면 에러를 발생시킨다..")
    @Test
    void validateCommandInputSizeFailTest() {
        assertThatThrownBy(() -> new Move(List.of("move", "ff", "f0", " ")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("게임 이동 명령어 입력 형식이 올바르지 않습니다.");
    }
}
