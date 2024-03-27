package chess.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.board.ChessBoard;
import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.Position;
import chess.fixture.PositionFixtures;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameTest {
    @DisplayName("턴에 맞는 움직임을 시도하면 예외를 발생시키지 않는다.")
    @Test
    void should_DoesNotThrowAnyException_When_MovementTurnIsValid() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(PositionFixtures.A1, new King(Team.WHITE));
        board.put(PositionFixtures.A8, new King(Team.BLACK));
        ChessBoard chessBoard = new ChessBoard(board);
        ChessGame chessGame = new ChessGame(chessBoard);

        assertThatCode(() -> chessGame.move(PositionFixtures.A1, PositionFixtures.A2))
                .doesNotThrowAnyException();
    }

    @DisplayName("턴에 맞지 않는 움직임을 시도하면 예외를 발생시킨다")
    @Test
    void should_ThrowException_When_IllegalTurnMove() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(PositionFixtures.A1, new King(Team.WHITE));
        board.put(PositionFixtures.A8, new King(Team.BLACK));
        ChessBoard chessBoard = new ChessBoard(board);
        ChessGame chessGame = new ChessGame(chessBoard);

        assertThatThrownBy(() -> chessGame.move(PositionFixtures.A8, PositionFixtures.A7))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
