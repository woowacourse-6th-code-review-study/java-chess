package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    //TODO 다른 테스트도 추가하기
    @DisplayName("초기 상태의 기물 점수를 계산한다.")
    @Test
    void calculateInitBoard() {
        //given
        final ChessGame chessGame = ChessGame.setupStartingPosition();
        final Score expected = new Score(38.0F);

        //when then
        assertAll(
                () -> assertThat(chessGame.calculateScore(Camp.WHITE)).isEqualTo(expected),
                () -> assertThat(chessGame.calculateScore(Camp.BLACK)).isEqualTo(expected)
        );
    }
}
