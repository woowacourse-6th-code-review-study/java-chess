package model.piece;

import static model.Fixtures.A2;
import static model.Fixtures.A4;
import static model.Fixtures.A5;
import static model.Fixtures.A6;
import static model.Fixtures.A7;
import static model.Fixtures.A8;
import static model.Fixtures.B2;
import static model.Fixtures.B3;
import static model.Fixtures.B5;
import static model.Fixtures.B7;
import static model.Fixtures.C2;
import static model.Fixtures.C4;
import static model.Fixtures.D5;
import static model.Fixtures.D6;
import static model.Fixtures.D7;
import static model.Fixtures.G8;
import static model.Fixtures.H2;
import static model.Fixtures.H3;
import static model.Fixtures.H4;
import static model.Fixtures.H5;
import static model.Fixtures.H6;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import exception.InvalidMovingException;
import java.util.stream.Stream;
import model.Camp;
import model.ChessBoard;
import model.position.Moving;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PawnTest {

    @DisplayName("이동할 수 없는 경로면 예외를 발생시킨다.")
    @ParameterizedTest
    @MethodSource("cantMovableParameterProvider")
    void invalidRoute(final Moving moving) {
        final Pawn pawn = new Pawn(Camp.BLACK);

        assertAll(
                () -> assertThat(pawn.canMovable(moving)).isFalse(),
                () -> assertThatThrownBy(() -> pawn.getMoveRoute(moving))
                        .isInstanceOf(InvalidMovingException.class)
        );
    }

    static Stream<Arguments> cantMovableParameterProvider() {
        return Stream.of(
                Arguments.of(new Moving(A6, A4)),
                Arguments.of(new Moving(A7, A4)),
                Arguments.of(new Moving(A7, A8))
        );
    }

    @DisplayName("이동할 수 있다면 경로를 반환한다.")
    @ParameterizedTest
    @MethodSource("canMovableParameterProvider")
    void canMovable(final Moving moving) {
        final Pawn pawn = new Pawn(Camp.BLACK);

        assertThat(pawn.canMovable(moving)).isTrue();
    }

    static Stream<Arguments> canMovableParameterProvider() {
        return Stream.of(
                Arguments.of(new Moving(A7, A5)),
                Arguments.of(new Moving(A6, A5))
        );
    }

    @Test
    @DisplayName("앞에 기물이 있다면 전진이 불가하다.")
    void canNotGoForward() {
        //given
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        /*
        RNBQKBNR  8
        .PPPPPPP  7
        ........  6
        P.......  5
        p.......  4
        ........  3
        .ppppppp  2
        rnbqkbnr  1

        abcdefgh
         */

        //when
        chessBoard.move(new Moving(A2, A4));
        chessBoard.move(new Moving(A7, A5));

        final Moving forwadMoving = new Moving(A4, A5);

        //then
        assertThatThrownBy(() -> chessBoard.move(forwadMoving))
                .isInstanceOf(InvalidMovingException.class);
    }

    @Test
    @DisplayName("대각선에 기물이 있다면 이동이 가능하다. WHITE (위 오른쪽)")
    void canGoDiagonal1() {
        //given
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        /*
        RNBQKBNR  8
        P.PPPPPP  7
        ........  6
        .P......  5
        p.......  4
        ........  3
        .ppppppp  2
        rnbqkbnr  1

        abcdefgh
         */

        //when
        chessBoard.move(new Moving(A2, A4));
        chessBoard.move(new Moving(B7, B5));

        //then
        assertThatCode(() -> chessBoard.move(new Moving(A4, B5)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("대각선에 기물이 있다면 이동이 가능하다. BLACK (아래 오른쪽)")
    void canGoDiagonal2() {
        //given
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        /*
        RNBQKBNR  8
        P.PPPPPP  7
        ........  6
        .P......  5
        ..p.....  4
        .p......  3
        p..ppppp  2
        rnbqkbnr  1

        abcdefgh
         */

        //when
        chessBoard.move(new Moving(C2, C4));
        chessBoard.move(new Moving(B7, B5));
        chessBoard.move(new Moving(B2, B3));

        //then
        assertThatCode(() -> chessBoard.move(new Moving(B5, C4)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("대각선에 기물이 없다면 이동이 불가하다.")
    void canNotGoDiagonal() {
        //given
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        /*
        RNBQKB.R  8
        PPPPPPPP  7
        .......N  6
        ........  5
        p.......  4
        ........  3
        .ppppppp  2
        rnbqkbnr  1

        abcdefgh
         */

        //when
        chessBoard.move(new Moving(A2, A4));
        chessBoard.move(new Moving(G8, H6));

        final Moving diagonalMoving = new Moving(A4, B5);

        //then
        assertThatThrownBy(() -> chessBoard.move(diagonalMoving))
                .isInstanceOf(InvalidMovingException.class);
    }

    @Test
    @DisplayName("폰은 후진은 불가하다. WHITE")
    void canNotGoBackWhite() {
        //given
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        //when
        chessBoard.move(new Moving(H2, H4));
        chessBoard.move(new Moving(D7, D5));

        final Moving whiteBackMoving = new Moving(H4, H3);

        //then
        assertThatThrownBy(() -> chessBoard.move(whiteBackMoving))
                .isInstanceOf(InvalidMovingException.class);
    }

    @Test
    @DisplayName("폰은 후진은 불가하다. BLACK")
    void canNotGoBack2() {
        //given
        final ChessBoard chessBoard = ChessBoard.setupStartingPosition();

        //when
        chessBoard.move(new Moving(H2, H4));
        chessBoard.move(new Moving(D7, D5));
        chessBoard.move(new Moving(H4, H5));

        final Moving blackBackMoving = new Moving(D5, D6);

        //then
        assertThatThrownBy(() -> chessBoard.move(blackBackMoving))
                .isInstanceOf(InvalidMovingException.class);
    }
}