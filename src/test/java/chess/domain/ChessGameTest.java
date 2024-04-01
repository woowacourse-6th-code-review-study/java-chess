package chess.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import chess.domain.board.ChessBoard;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Rook;
import chess.domain.piece.Score;
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

    @DisplayName("배치된 기물에 맞추어 팀 점수를 계산할 수 있다")
    @Test
    void should_CalculateTeamScore() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(PositionFixtures.A1, new Knight(Team.WHITE));
        board.put(PositionFixtures.A8, new Rook(Team.WHITE));

        ChessBoard chessBoard = new ChessBoard(board);
        ChessGame chessGame = new ChessGame(chessBoard);

        assertThat(chessGame.calculateTeamScore(Team.WHITE)).isEqualTo(new Score(7.5));
    }

    @DisplayName("폰은 열이 겹치지 않는다면 1점으로 계산한다")
    @Test
    void should_CalculatePawnScoreAsOne_When_NoSameFilePawn() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(PositionFixtures.A1, new Pawn(Team.WHITE));
        board.put(PositionFixtures.B1, new Pawn(Team.WHITE));

        ChessBoard chessBoard = new ChessBoard(board);
        ChessGame chessGame = new ChessGame(chessBoard);

        assertThat(chessGame.calculateTeamScore(Team.WHITE)).isEqualTo(new Score(2));
    }

    @DisplayName("폰은 열이 겹치지 않는다면 1점으로 계산한다")
    @Test
    void should_CalculatePawnScoreAsHalf_When_SameFilePawnExist() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(PositionFixtures.A1, new Pawn(Team.WHITE));
        board.put(PositionFixtures.A2, new Pawn(Team.WHITE));

        ChessBoard chessBoard = new ChessBoard(board);
        ChessGame chessGame = new ChessGame(chessBoard);

        assertThat(chessGame.calculateTeamScore(Team.WHITE)).isEqualTo(new Score(1));
    }

    @DisplayName("체스 게임 도메인은 점수가 더 높은 팀을 가려낼 수 있다")
    @Test
    void should_ChessGameCanSelectHigherScoreTeam() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(PositionFixtures.A1, new Pawn(Team.WHITE));
        board.put(PositionFixtures.A2, new Pawn(Team.WHITE));

        board.put(PositionFixtures.A3, new Knight(Team.BLACK));
        board.put(PositionFixtures.A8, new Rook(Team.BLACK));

        ChessBoard chessBoard = new ChessBoard(board);
        ChessGame chessGame = new ChessGame(chessBoard);

        assertThat(chessGame.selectHigherScoreTeam()).isEqualTo(Team.BLACK);
    }
}

