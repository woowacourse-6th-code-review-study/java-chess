package chess.domain.board;

import static chess.fixture.PositionFixtures.A1;
import static chess.fixture.PositionFixtures.A2;
import static chess.fixture.PositionFixtures.A3;
import static chess.fixture.PositionFixtures.A4;
import static chess.fixture.PositionFixtures.B3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.piece.Bishop;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Rook;
import chess.domain.piece.Score;
import chess.domain.piece.Team;
import chess.domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardTest {

    @DisplayName("체스보드의 특정위치에 기물이 없는 지 확인할 수 있다")
    @Test
    void should_CheckPositionEmptiness_When_GivenPosition() {
        Map<Position, Piece> positionPiece = new HashMap<>();
        Position position = A1;
        positionPiece.put(position, new King(Team.BLACK));
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThat(chessBoard.positionIsEmpty(A2)).isTrue();
    }

    @DisplayName("체스보드의 특정위치에 기물이 있는지 확인할 수 있다")
    @Test
    void should_CheckPositionNotEmptiness_When_GivenPosition() {
        Map<Position, Piece> positionPiece = new HashMap<>();
        Position position = A1;
        positionPiece.put(position, new King(Team.BLACK));
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThat(chessBoard.positionIsEmpty(A1)).isFalse();
    }

    @DisplayName("체스보드 특정 위치의 기물을 가져올 수 있다")
    @Test
    void should_GetPieceByPosition_When_GiveTargetPosition() {
        Map<Position, Piece> positionPiece = new HashMap<>();
        Position position = A1;
        positionPiece.put(position, new King(Team.BLACK));
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThat(chessBoard.findPieceByPosition(position)).isInstanceOf(King.class);
    }

    @DisplayName("체스보드 특정 위치의 기물을 가져올 때 없으면 예외를 발생시킨다")
    @Test
    void should_ThrowNoSuchElementException_When_TargetPositionIsEmpty() {
        Map<Position, Piece> positionPiece = new HashMap<>();
        Position position = A1;
        positionPiece.put(position, new King(Team.BLACK));
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThatThrownBy(() -> chessBoard.findPieceByPosition(A2))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("해당 위치에 기물이 존재하지 않습니다.");
    }

    @DisplayName("체스보드 특정 위치에 적대적 기물이 없는 지 확인할 수 있다")
    @Test
    void should_CheckThereIsHostilePiece_When_DestinationIsGiven() {
        Map<Position, Piece> positionPiece = new HashMap<>();
        King hostile = new King(Team.WHITE);
        positionPiece.put(A1, hostile);
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertAll(
                () -> assertThat(chessBoard.isNoHostilePieceAt(A2, Team.BLACK)).isTrue(),
                () -> assertThat(chessBoard.isNoHostilePieceAt(A1, Team.BLACK)).isFalse()
        );
    }

    @DisplayName("팀의 기본 점수를 계산할 수 있다")
    @Test
    void should_CalculateDefaultScore() {
        Map<Position, Piece> positionPiece = new HashMap<>();

        positionPiece.put(A1, new Bishop(Team.WHITE));
        positionPiece.put(A2, new Knight(Team.WHITE));
        positionPiece.put(A3, new Rook(Team.BLACK));
        positionPiece.put(A4, new Rook(Team.BLACK));

        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertAll(
                () -> assertThat(chessBoard.calcualteDefaultScore(Team.WHITE)).isEqualTo(new Score(5.5)),
                () -> assertThat(chessBoard.calcualteDefaultScore(Team.BLACK)).isEqualTo(new Score(10))
        );
    }

    @DisplayName("세로 라인이 같은 폰이 총 몇개인지 셀 수 있다")
    @Test
    void should_CountSameFilePawn() {
        Map<Position, Piece> positionPiece = new HashMap<>();

        positionPiece.put(A1, new Pawn(Team.WHITE));
        positionPiece.put(A2, new Pawn(Team.WHITE));
        positionPiece.put(B3, new Pawn(Team.BLACK));
        positionPiece.put(A4, new Pawn(Team.BLACK));

        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertAll(
                () -> assertThat(chessBoard.countSameFilePawn(Team.WHITE)).isEqualTo(2),
                () -> assertThat(chessBoard.countSameFilePawn(Team.BLACK)).isEqualTo(0)
        );
    }

    @DisplayName("흑팀의 킹이 살아있는지 확인할 수 있다")
    @Test
    void should_CheckBlackKingIsAlive() {
        Map<Position, Piece> positionPiece = new HashMap<>();

        positionPiece.put(A1, new King(Team.WHITE));
        positionPiece.put(B3, new King(Team.BLACK));

        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThat(chessBoard.isBlackKingAlive()).isTrue();
    }

    @DisplayName("백팀의 킹이 살아있는지 확인할 수 있다")
    @Test
    void should_CheckWhiteKingIsAlive() {
        Map<Position, Piece> positionPiece = new HashMap<>();

        positionPiece.put(A1, new King(Team.WHITE));
        positionPiece.put(B3, new King(Team.BLACK));

        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThat(chessBoard.isWhiteKingAlive()).isTrue();
    }
}
