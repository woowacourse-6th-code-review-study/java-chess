package chess.domain.movement.pawn;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class WhitePawnDefaultMovementTest {

    private static final boolean NOT_EXIST_ENEMY = false;

    @Test
    @DisplayName("이동 가능한지 확인한다.")
    void isMovableTest() {
        Position start = new Position(File.D, Rank.FOUR);
        Position end = new Position(File.D, Rank.FIVE);
        WhitePawnDefaultMovement whitePawnDefaultMovement = new WhitePawnDefaultMovement();

        assertThat(whitePawnDefaultMovement.isMovable(start, end, NOT_EXIST_ENEMY)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"B, SIX", "F, SIX", "D, THREE", "D, SIX"})
    @DisplayName("이동 가능한지 확인한다.")
    void isMovableTest_false(File file, Rank rank) {
        Position start = new Position(File.D, Rank.FOUR);
        Position end = new Position(file, rank);
        WhitePawnDefaultMovement whitePawnDefaultMovement = new WhitePawnDefaultMovement();

        assertThat(whitePawnDefaultMovement.isMovable(start, end, NOT_EXIST_ENEMY)).isFalse();
    }

    @Test
    @DisplayName("도착 위치에 적이 있을 경우, 이동 불가능하다.")
    void isMovableTest_existEnemy() {
        Position start = new Position(File.B, Rank.FOUR);
        Position end = start.moveToNorth();
        boolean hasEnemy = true;
        WhitePawnDefaultMovement whitePawnDefaultMovement = new WhitePawnDefaultMovement();

        assertThat(whitePawnDefaultMovement.isMovable(start, end, hasEnemy)).isFalse();
    }

    @Test
    @DisplayName("이동 경로를 알 수 있다.")
    void findPathTest() {
        Position start = new Position(File.D, Rank.FOUR);
        Position end = new Position(File.D, Rank.FIVE);
        WhitePawnDefaultMovement whitePawnDefaultMovement = new WhitePawnDefaultMovement();

        assertThat(whitePawnDefaultMovement.findPath(start, end, NOT_EXIST_ENEMY))
                .isEmpty();
    }
}