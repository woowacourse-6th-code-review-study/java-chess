package model;

import static model.Fixtures.A1;
import static model.Fixtures.A2;
import static model.Fixtures.A3;
import static model.Fixtures.A4;
import static model.Fixtures.A5;
import static model.Fixtures.A6;
import static model.Fixtures.A7;
import static model.Fixtures.A8;
import static model.Fixtures.B1;
import static model.Fixtures.B2;
import static model.Fixtures.B5;
import static model.Fixtures.B7;
import static model.Fixtures.B8;
import static model.Fixtures.C1;
import static model.Fixtures.C2;
import static model.Fixtures.C7;
import static model.Fixtures.C8;
import static model.Fixtures.D1;
import static model.Fixtures.D2;
import static model.Fixtures.D7;
import static model.Fixtures.D8;
import static model.Fixtures.E1;
import static model.Fixtures.E2;
import static model.Fixtures.E4;
import static model.Fixtures.E5;
import static model.Fixtures.E7;
import static model.Fixtures.E8;
import static model.Fixtures.F1;
import static model.Fixtures.F2;
import static model.Fixtures.F7;
import static model.Fixtures.F8;
import static model.Fixtures.G1;
import static model.Fixtures.G2;
import static model.Fixtures.G7;
import static model.Fixtures.G8;
import static model.Fixtures.H1;
import static model.Fixtures.H2;
import static model.Fixtures.H7;
import static model.Fixtures.H8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import exception.InvalidTurnException;
import exception.PieceDoesNotExistException;
import exception.PieceExistInRouteException;
import java.util.HashMap;
import java.util.Map;
import model.piece.Bishop;
import model.piece.King;
import model.piece.Knight;
import model.piece.Pawn;
import model.piece.Piece;
import model.piece.Queen;
import model.piece.Rook;
import model.position.Moving;
import model.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardTest {

    @DisplayName("초기에는 32개의 기물이 생성된다.")
    @Test
    void checkPiecesCount() {
        //given && when
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        //then
        final Map<Position, Piece> board = chessBoard.getBoard();
        assertThat(board.keySet()).hasSize(32);
    }

    @DisplayName("기물들의 시작 위치를 확인한다.")
    @Test
    void checkStartingPosition() {
        //given && when
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        final Map<Position, Piece> board = chessBoard.getBoard();

        final Map<Position, Piece> expected = new HashMap<>();

        // black
        expected.put(A8, new Rook(Camp.BLACK));
        expected.put(B8, new Knight(Camp.BLACK));
        expected.put(C8, new Bishop(Camp.BLACK));
        expected.put(D8, new Queen(Camp.BLACK));
        expected.put(E8, new King(Camp.BLACK));
        expected.put(F8, new Bishop(Camp.BLACK));
        expected.put(G8, new Knight(Camp.BLACK));
        expected.put(H8, new Rook(Camp.BLACK));
        expected.put(A7, new Pawn(Camp.BLACK));
        expected.put(B7, new Pawn(Camp.BLACK));
        expected.put(C7, new Pawn(Camp.BLACK));
        expected.put(D7, new Pawn(Camp.BLACK));
        expected.put(E7, new Pawn(Camp.BLACK));
        expected.put(F7, new Pawn(Camp.BLACK));
        expected.put(G7, new Pawn(Camp.BLACK));
        expected.put(H7, new Pawn(Camp.BLACK));

        //white
        expected.put(A1, new Rook(Camp.WHITE));
        expected.put(B1, new Knight(Camp.WHITE));
        expected.put(C1, new Bishop(Camp.WHITE));
        expected.put(D1, new Queen(Camp.WHITE));
        expected.put(E1, new King(Camp.WHITE));
        expected.put(F1, new Bishop(Camp.WHITE));
        expected.put(G1, new Knight(Camp.WHITE));
        expected.put(H1, new Rook(Camp.WHITE));
        expected.put(A2, new Pawn(Camp.WHITE));
        expected.put(B2, new Pawn(Camp.WHITE));
        expected.put(C2, new Pawn(Camp.WHITE));
        expected.put(D2, new Pawn(Camp.WHITE));
        expected.put(E2, new Pawn(Camp.WHITE));
        expected.put(F2, new Pawn(Camp.WHITE));
        expected.put(G2, new Pawn(Camp.WHITE));
        expected.put(H2, new Pawn(Camp.WHITE));

        //then
        assertThat(board).isEqualTo(expected);
    }

    @DisplayName("해당 위치에 기물이 없는 경우 예외가 발생한다.")
    @Test
    void failToMoveIfNoPiece() {
        //given
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        final Moving moving = new Moving(E4, E5);

        //when & then
        assertThatThrownBy(() -> chessBoard.move(moving))
                .isInstanceOf(PieceDoesNotExistException.class);
    }

    @Test
    @DisplayName("자신의 기물이 아니면 예외가 발생한다.")
    void failToMoveIfDifferentCamp() {
        //given
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        //when && then
        final Moving moving = new Moving(A7, A6);

        assertThatThrownBy(() -> chessBoard.move(moving))
                .isInstanceOf(InvalidTurnException.class);
    }

    @DisplayName("이동 경로에 기물이 있으면 예외를 발생시킨다.")
    @Test
    void failToMoveIfContainPieceInRoute() {
        //given
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        //when && then
        final Moving moving = new Moving(A1, A3);

        assertThatThrownBy(() -> chessBoard.move(moving))
                .isInstanceOf(PieceExistInRouteException.class);
    }

    @DisplayName("도착 지점에 같은 진영의 기물이 있으면 예외를 발생시킨다.")
    @Test
    void failToMoveIfContainsPieceInTargetPosition() {
        //given
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        //when
        chessBoard.move(new Moving(A2, A4)); // WHITE
        chessBoard.move(new Moving(A7, A5)); // BLACK

        //then
        final Moving moving = new Moving(A1, A4);

        assertThatThrownBy(() -> chessBoard.move(moving))
                .isInstanceOf(PieceExistInRouteException.class);
    }

    @DisplayName("기물이 잡히면 체스보드에서 제거된다.")
    @Test
    void checkRemovePiece() {
        //given
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        //when
        chessBoard.move(new Moving(A2, A4));
        chessBoard.move(new Moving(B7, B5));
        chessBoard.move(new Moving(A4, B5));

        //then
        assertThat(chessBoard.getBoard()).hasSize(31);
    }

    @DisplayName("선공은 WHITE이다.")
    @Test
    void checkFirstAttack() {
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        assertThat(chessBoard.getCamp()).isEqualTo(Camp.WHITE);
    }

    @DisplayName("후공은 BLACK이다.")
    @Test
    void checkSecondAttack() {
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        chessBoard.move(new Moving(A2, A3));

        assertThat(chessBoard.getCamp()).isEqualTo(Camp.BLACK);
    }
}
