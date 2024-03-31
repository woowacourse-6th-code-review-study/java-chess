package chess.domain.board;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import chess.domain.board.position.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RowTest {

    @Test
    @DisplayName("거리 만큼 이동한 Row를 반환한다.")
    void calculateNextRowSuccessTest() {
        Row row = Row.FOUR;

        assertAll(
                () -> assertEquals(Row.FIVE, row.calculateNextRow(1)),
                () -> assertEquals(Row.THREE, row.calculateNextRow(-1)),
                () -> assertEquals(Row.FOUR, row.calculateNextRow(0))
        );
    }

    @Test
    @DisplayName("거리 만큼 이동한 값이 보드판을 벗어난 경우 에러를 반환한다.")
    void calculateNextRowFailTest() {
        Row row = Row.FOUR;

        assertThatThrownBy(() -> row.calculateNextRow(-5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("다음 위치로 이동할 수 있는 열이 없습니다.");
    }

    @Test
    @DisplayName("거리 만큼 이동한 값이 보드판을 벗어난 경우 false를 반환한다.")
    void isNextInRange() {
        assertAll(
                () -> assertFalse(Row.EIGHT.isNextInRange(1)),
                () -> assertFalse(Row.ONE.isNextInRange(-1))
        );
    }

    @Test
    @DisplayName("인덱스로 일치하는 열을 반환한다.")
    void findByIndexTest() {
        assertAll(
                () -> assertEquals(Row.EIGHT, Row.findByIndex(8)),
                () -> assertEquals(Row.SEVEN, Row.findByIndex(7)),
                () -> assertEquals(Row.SIX, Row.findByIndex(6)),
                () -> assertEquals(Row.FIVE, Row.findByIndex(5)),
                () -> assertEquals(Row.FOUR, Row.findByIndex(4)),
                () -> assertEquals(Row.THREE, Row.findByIndex(3)),
                () -> assertEquals(Row.TWO, Row.findByIndex(2)),
                () -> assertEquals(Row.ONE, Row.findByIndex(1))
        );
    }

    @Test
    @DisplayName("인덱스로 일치하는 열 반환에 실패하면 에러를 반환한다.")
    void findByIndexFailTest() {
        assertThatThrownBy(() -> Row.findByIndex(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("열과 일치하는 인덱스가 없습니다.");
    }
}
