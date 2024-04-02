package chess.domain.board;

import static chess.domain.Position.A1;
import static chess.domain.Position.A3;
import static chess.domain.Position.A4;
import static chess.domain.Position.A6;
import static chess.domain.Position.A8;
import static chess.domain.Position.B1;
import static chess.domain.Position.B2;
import static chess.domain.Position.B6;
import static chess.domain.Position.C1;
import static chess.domain.Position.C2;
import static chess.domain.Position.C3;
import static chess.domain.Position.C6;
import static chess.domain.Position.D1;
import static chess.domain.Position.D2;
import static chess.domain.Position.D3;
import static chess.domain.Position.D4;
import static chess.domain.Position.D5;
import static chess.domain.Position.D7;
import static chess.domain.Position.D8;
import static chess.domain.Position.E1;
import static chess.domain.Position.E3;
import static chess.domain.Position.F2;
import static chess.domain.Position.H1;
import static chess.domain.Position.H2;
import static chess.domain.piece.PieceMoveResult.FAILURE;
import static chess.domain.piece.PieceMoveResult.SUCCESS;
import static chess.domain.piece.Team.BLACK;
import static chess.domain.piece.Team.WHITE;

import chess.domain.Position;
import chess.domain.piece.Bishop;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceMoveResult;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.piece.Team;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChessBoardTest {

    public static Stream<Arguments> moveSuccessParameters() {
        return Stream.of(
                Arguments.of(D2, D4), Arguments.of(D2, D3), Arguments.of(A1, A6), Arguments.of(B1, C3),
                Arguments.of(C1, A3), Arguments.of(D1, A4), Arguments.of(E1, F2)
        );
    }

    public static Stream<Arguments> moveFailureCauseInvalidMoveParameters() {
        return Stream.of(
                Arguments.of(D2, D5), Arguments.of(D2, C3), Arguments.of(A1, H2), Arguments.of(A1, B6),
                Arguments.of(B1, B2), Arguments.of(C1, C6), Arguments.of(D1, D3), Arguments.of(D1, C3),
                Arguments.of(E1, E3)
        );
    }

    public static Stream<Arguments> whichTeamParameters() {
        return Stream.of(
                Arguments.of(WHITE),
                Arguments.of(BLACK)
        );
    }

    @ParameterizedTest
    @MethodSource("moveSuccessParameters")
    @DisplayName("정상적인 경우에 이동이 성공하는지 검증")
    void moveSuccess(Position from, Position to) {
        ChessBoard chessBoard = new ChessBoard(new King(A8, WHITE), new King(H1, BLACK), new Pawn(D2, WHITE),
                new Rook(A1, WHITE), new King(E1, WHITE),
                new Knight(B1, WHITE), new Bishop(C1, WHITE), new Queen(D1, WHITE));
        PieceMoveResult moveResult = chessBoard.move(from, to);
        Assertions.assertThat(moveResult).isEqualTo(SUCCESS);
    }

    @ParameterizedTest
    @MethodSource("moveFailureCauseInvalidMoveParameters")
    @DisplayName("해당 위치가 이동이 불가능한 위치라서 이동이 실패하는지 검증")
    void moveFailureCauseInvalidMove(Position from, Position to) {
        ChessBoard chessBoard = new ChessBoard(new King(A8, WHITE), new King(H1, BLACK), new Pawn(D2, WHITE),
                new Rook(A1, WHITE), new King(E1, WHITE),
                new Knight(B1, WHITE), new Bishop(C1, WHITE), new Queen(D1, WHITE));
        PieceMoveResult moveResult = chessBoard.move(from, to);
        Assertions.assertThat(moveResult).isEqualTo(FAILURE);
    }

    @Test
    @DisplayName("해당 위치에 말이 없어서 이동이 실패하는지 검증")
    void moveFailureCausePieceNotFound() {
        ChessBoard chessBoard = new ChessBoard(new King(A8, WHITE), new King(H1, BLACK), new Pawn(D2, WHITE));
        PieceMoveResult moveResult = chessBoard.move(D3, D4);
        Assertions.assertThat(moveResult).isEqualTo(FAILURE);
    }

    @Test
    @DisplayName("팀의 이동 차례가 아니라서 이동이 실패하는지 검증")
    void moveFailureCauseInvalidTurn() {
        ChessBoard chessBoard = new ChessBoard(new King(A8, WHITE), new King(H1, BLACK), new Pawn(D2, WHITE),
                new Pawn(D7, BLACK));
        chessBoard.move(D2, D4);

        PieceMoveResult moveResult = chessBoard.move(D4, D5);
        Assertions.assertThat(moveResult).isEqualTo(FAILURE);
    }

    @Test
    @DisplayName("상대 말을 잡으면 그 말이 보드에서 사라지는지 검증")
    void catchSuccess() {
        Pawn survivor = new Pawn(D2, WHITE);
        King whiteKing = new King(A8, WHITE);
        King blackKing = new King(H1, BLACK);
        ChessBoard chessBoard = new ChessBoard(whiteKing, blackKing, survivor, new Pawn(C3, BLACK));
        chessBoard.move(D2, C3);
        List<Piece> piecesOnBoard = chessBoard.getPiecesOnBoard();
        Assertions.assertThat(piecesOnBoard)
                .containsExactly(whiteKing, blackKing, survivor);
    }

    @Test
    @DisplayName("잘못된 이동을 시도했을 때 차례가 넘어가지 않는지 검증")
    void validateTurnNotChangeWhenInvalidMove() {
        ChessBoard chessBoard = new ChessBoard(new King(A8, WHITE), new King(H1, BLACK), new Pawn(D2, WHITE),
                new Pawn(C3, BLACK));
        chessBoard.move(D2, D8);
        PieceMoveResult moveResult = chessBoard.move(C3, C2);
        Assertions.assertThat(moveResult).isEqualTo(FAILURE);
    }

    @Test
    @DisplayName("이동이 성공했을 때 차례가 넘어가는지 검증")
    void validateTurnChangeWhenValidMove() {
        ChessBoard chessBoard = new ChessBoard(new King(A8, WHITE), new King(H1, BLACK), new Pawn(D2, WHITE),
                new Pawn(C3, BLACK));
        chessBoard.move(D2, D3);
        PieceMoveResult moveResult = chessBoard.move(C3, C2);
        Assertions.assertThat(moveResult).isEqualTo(SUCCESS);
    }

    @ParameterizedTest
    @MethodSource("whichTeamParameters")
    @DisplayName("체스 판 위의 특정 위치의 말의 팀을 잘 판단하는지 검증")
    void whichTeam(Team team) {
        List<Piece> pieces = List.of(new Pawn(D2, team));
        ChessBoard chessBoard = new ChessBoard(pieces);
        Team actual = chessBoard.whichTeam(D2).orElseThrow();
        Assertions.assertThat(actual)
                .isEqualTo(team);
    }

    @Test
    @DisplayName("해당 위치에 말이 없는 경우 팀을 판단할 수 없는지 검증")
    void whichTeamWhenEmpty() {
        ChessBoard chessBoard = new ChessBoard(new King(A8, WHITE), new King(H1, BLACK));
        Optional<Team> actual = chessBoard.whichTeam(D2);
        Assertions.assertThat(actual)
                .isEmpty();
    }
}
