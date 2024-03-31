package chess.domain.game;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomNameTest {

    @DisplayName("게임방 이름 길이가 16글자 이하면 정상 생성된다.")
    @Test
    void validateNameLengthSuccessTest() {
        assertThatNoException()
                .isThrownBy(() -> new RoomName("----------------"));
    }

    @DisplayName("게임방 이름 길이가 16글자를 초과하면 에러를 발생시킨다.")
    @Test
    void validateNameLengthFailTest() {
        assertThatThrownBy(() -> new RoomName("-----------------"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("게임 방 이름 길이는 최대 16글자까지 가능합니다.");
    }
}
