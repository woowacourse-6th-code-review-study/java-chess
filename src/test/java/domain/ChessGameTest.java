package domain;

import domain.board.ChessBoard;
import domain.piece.Color;
import domain.piece.nonpawn.King;
import domain.position.Position;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChessGameTest {
    @Test
    void 게임을_시작하기_전에_말을_움직일_수_없다() {
        ChessGame game = new ChessGame(new ChessBoard(Map.of()));

        assertThatThrownBy(() -> game.move(new Position("A1"), new Position("A2")))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("게임을 시작해 주세요.");
    }

    @Test
    void 게임을_시작하기_전에_종료할_수_없다() {
        ChessGame game = new ChessGame(new ChessBoard(Map.of()));

        assertThatThrownBy(game::end)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("게임을 시작해 주세요.");
    }

    @Test
    void 게임을_시작하고_다시_시작할_수_없다() {
        ChessGame game = new ChessGame(new ChessBoard(Map.of()));

        game.start();

        assertThatThrownBy(game::start)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("게임이 이미 시작됐습니다.");
    }

    @Test
    void 게임이_생성되기만_하면_게임은_진행_상태이다() {
        ChessGame game = new ChessGame(new ChessBoard(Map.of()));

        assertThat(game.isPlaying()).isTrue();
    }

    @Test
    void end를_호출하기_전까지_게임은_진행_상태이다() {
        ChessGame game = new ChessGame(new ChessBoard(Map.of()));

        game.start();

        assertThat(game.isPlaying()).isTrue();
    }

    @Test
    void end를_호출되면_게임은_종료된다() {
        ChessGame game = new ChessGame(new ChessBoard(Map.of()));

        game.start();
        game.end();

        assertThat(game.isPlaying()).isFalse();
    }

    @Test
    void 피스를_움직이고_왕이_죽으면_게임은_오버된다() {
        Position A1 = new Position("A1");
        Position A2 = new Position("A2");
        ChessGame game = new ChessGame(new ChessBoard(Map.of(
                A1, new King(Color.WHITE),
                A2, new King(Color.BLACK))));

        game.start();
        game.move(A1, A2);

        assertThat(game.isGameOver()).isTrue();
    }

    @Test
    void 모든_KING이_살아있으면_게임은_오버되지_않는다() {
        ChessGame game = new ChessGame(new ChessBoard(Map.of(
                new Position("A1"), new King(Color.WHITE),
                new Position("A2"), new King(Color.BLACK))));

        assertThat(game.isGameOver()).isFalse();
    }

    @Test
    void KING이_없으면_게임_오버_상태이다() {
        ChessGame game = new ChessGame(new ChessBoard(Map.of()));

        assertThat(game.isGameOver()).isTrue();
    }
}
