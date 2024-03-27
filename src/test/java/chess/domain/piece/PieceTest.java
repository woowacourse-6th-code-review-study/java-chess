package chess.domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.board.ChessBoard;
import chess.domain.position.Position;
import chess.fixture.PositionFixtures;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {
    private Piece blackPiece;
    private Piece whitePiece;


    @BeforeEach
    void setUp() {
        blackPiece = new Piece(Team.BLACK) {
            @Override
            boolean canNotMoveByItsOwnInPassing(Position start, Position destination) {
                return false;
            }

            @Override
            boolean canNotMoveByBoardStatus(Position start, Position destination, ChessBoard chessBoard) {
                return false;
            }
        };

        whitePiece = new Piece(Team.WHITE) {
            @Override
            boolean canNotMoveByItsOwnInPassing(Position start, Position destination) {
                return false;
            }

            @Override
            boolean canNotMoveByBoardStatus(Position start, Position destination, ChessBoard chessBoard) {
                return false;
            }
        };
    }

    @DisplayName("기물의 팀이 흑팀인지 확인할 수 있다")
    @Test
    void should_CheckPieceIsBlackTeam() {
        assertAll(
                () -> assertThat(blackPiece.isBlackTeam()).isTrue(),
                () -> assertThat(whitePiece.isBlackTeam()).isFalse()
        );
    }

    @DisplayName("기물간 서로 같은 팀인지 다른 팀인지 확인할 수 있다")
    @Test
    void should_ComparePieceTeam() {
        assertAll(
                () -> assertThat(blackPiece.isOtherTeam(Team.WHITE)).isTrue(),
                () -> assertThat(whitePiece.isOtherTeam(Team.WHITE)).isFalse()
        );
    }

    @DisplayName("모든 기물은 시작위치와 같은 곳으로 이동하지 못한다")
    @Test
    void should_AllPieceCanNotMove_When_StartAndDestinationIsSame() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(PositionFixtures.A1, whitePiece);
        ChessBoard chessBoard = new ChessBoard(board);
        boolean canMoveSamePosition = whitePiece.canMove(PositionFixtures.A1, PositionFixtures.A1, chessBoard);

        assertThat(canMoveSamePosition).isFalse();
    }

    @DisplayName("모든 기물은 도착지에 아군이 있는 경우 이동하지 못한다")
    @Test
    void should_AllPieceCanNotMove_When_FriendlyPieceAtDestination() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(PositionFixtures.A1, whitePiece);
        board.put(PositionFixtures.A2, new King(Team.WHITE));
        ChessBoard chessBoard = new ChessBoard(board);
        boolean canMoveFriendlyPiecePosition = whitePiece.canMove(PositionFixtures.A1, PositionFixtures.A2, chessBoard);

        assertThat(canMoveFriendlyPiecePosition).isFalse();
    }
}
