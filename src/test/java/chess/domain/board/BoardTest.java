package chess.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import chess.domain.board.position.Column;
import chess.domain.board.position.Position;
import chess.domain.board.position.Row;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Map<Position, Piece> map;

    @BeforeEach
    void setUp() {
        map = new Board().getBoard();
    }

    @Test
    @DisplayName("보드 생성 시 32개의 기물이 초기화된다.")
    void printMap() {
        assertThat(map).hasSize(32);
    }

    @Nested
    @DisplayName("체스 보드 초기화 정상 테스트")
    class BoardInitializeTest {
        @Test
        @DisplayName("검정 폰이 정상적으로 초기화된다.")
        void blackPawnInitializeTest() {
            assertAll(
                    () -> assertEquals(map.get(new Position(Row.SEVEN, Column.A)), new Piece(PieceType.BLACK_PAWN, Color.BLACK)),
                    () -> assertEquals(map.get(new Position(Row.SEVEN, Column.H)), new Piece(PieceType.BLACK_PAWN, Color.BLACK)),
                    () -> assertEquals(map.get(new Position(Row.SEVEN, Column.B)), new Piece(PieceType.BLACK_PAWN, Color.BLACK)),
                    () -> assertEquals(map.get(new Position(Row.SEVEN, Column.G)), new Piece(PieceType.BLACK_PAWN, Color.BLACK)),
                    () -> assertEquals(map.get(new Position(Row.SEVEN, Column.C)), new Piece(PieceType.BLACK_PAWN, Color.BLACK)),
                    () -> assertEquals(map.get(new Position(Row.SEVEN, Column.F)), new Piece(PieceType.BLACK_PAWN, Color.BLACK)),
                    () -> assertEquals(map.get(new Position(Row.SEVEN, Column.D)), new Piece(PieceType.BLACK_PAWN, Color.BLACK)),
                    () -> assertEquals(map.get(new Position(Row.SEVEN, Column.E)), new Piece(PieceType.BLACK_PAWN, Color.BLACK)));
        }

        @Test
        @DisplayName("흰색 폰이 정상적으로 초기화된다.")
        void whitePawnInitializeTest() {
            assertAll(
                    () -> assertEquals(map.get(new Position(Row.TWO, Column.A)), new Piece(PieceType.WHITE_PAWN, Color.WHITE)),
                    () -> assertEquals(map.get(new Position(Row.TWO, Column.H)), new Piece(PieceType.WHITE_PAWN, Color.WHITE)),
                    () -> assertEquals(map.get(new Position(Row.TWO, Column.B)), new Piece(PieceType.WHITE_PAWN, Color.WHITE)),
                    () -> assertEquals(map.get(new Position(Row.TWO, Column.G)), new Piece(PieceType.WHITE_PAWN, Color.WHITE)),
                    () -> assertEquals(map.get(new Position(Row.TWO, Column.C)), new Piece(PieceType.WHITE_PAWN, Color.WHITE)),
                    () -> assertEquals(map.get(new Position(Row.TWO, Column.F)), new Piece(PieceType.WHITE_PAWN, Color.WHITE)),
                    () -> assertEquals(map.get(new Position(Row.TWO, Column.D)), new Piece(PieceType.WHITE_PAWN, Color.WHITE)),
                    () -> assertEquals(map.get(new Position(Row.TWO, Column.E)), new Piece(PieceType.WHITE_PAWN, Color.WHITE)));
        }

        @Test
        @DisplayName("폰을 제외한 검정 기물들이 정상적으로 초기화된다.")
        void blackSpecialPieceInitializeTest() {
            assertAll(
                    () -> assertEquals(map.get(new Position(Row.EIGHT, Column.A)), new Piece(PieceType.ROOK, Color.BLACK)),
                    () -> assertEquals(map.get(new Position(Row.EIGHT, Column.H)), new Piece(PieceType.ROOK, Color.BLACK)),
                    () -> assertEquals(map.get(new Position(Row.EIGHT, Column.B)), new Piece(PieceType.KNIGHT, Color.BLACK)),
                    () -> assertEquals(map.get(new Position(Row.EIGHT, Column.G)), new Piece(PieceType.KNIGHT, Color.BLACK)),
                    () -> assertEquals(map.get(new Position(Row.EIGHT, Column.C)), new Piece(PieceType.BISHOP, Color.BLACK)),
                    () -> assertEquals(map.get(new Position(Row.EIGHT, Column.F)), new Piece(PieceType.BISHOP, Color.BLACK)),
                    () -> assertEquals(map.get(new Position(Row.EIGHT, Column.D)), new Piece(PieceType.QUEEN, Color.BLACK)),
                    () -> assertEquals(map.get(new Position(Row.EIGHT, Column.E)), new Piece(PieceType.KING, Color.BLACK)));
        }

        @Test
        @DisplayName("폰을 제외 흰색 기물들이 정상적으로 초기화된다.")
        void whiteSpecialPieceInitializeTest() {
            assertAll(
                    () -> assertEquals(map.get(new Position(Row.ONE, Column.A)), new Piece(PieceType.ROOK, Color.WHITE)),
                    () -> assertEquals(map.get(new Position(Row.ONE, Column.H)), new Piece(PieceType.ROOK, Color.WHITE)),
                    () -> assertEquals(map.get(new Position(Row.ONE, Column.B)), new Piece(PieceType.KNIGHT, Color.WHITE)),
                    () -> assertEquals(map.get(new Position(Row.ONE, Column.G)), new Piece(PieceType.KNIGHT, Color.WHITE)),
                    () -> assertEquals(map.get(new Position(Row.ONE, Column.C)), new Piece(PieceType.BISHOP, Color.WHITE)),
                    () -> assertEquals(map.get(new Position(Row.ONE, Column.F)), new Piece(PieceType.BISHOP, Color.WHITE)),
                    () -> assertEquals(map.get(new Position(Row.ONE, Column.D)), new Piece(PieceType.QUEEN, Color.WHITE)),
                    () -> assertEquals(map.get(new Position(Row.ONE, Column.E)), new Piece(PieceType.KING, Color.WHITE)));
            }
    }

    @Nested
    @DisplayName("기물 점수 계산 테스트")
    class CalculateScoreTest {

        @Test
        void calculateTeamScoreTest() {
            Map<Position, Piece> map = new HashMap<>();

            map.put(new Position(Row.ONE, Column.B), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
            map.put(new Position(Row.TWO, Column.B), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
            map.put(new Position(Row.THREE, Column.B), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
            map.put(new Position(Row.ONE, Column.D), new Piece(PieceType.QUEEN, Color.WHITE));
            map.put(new Position(Row.ONE, Column.E), new Piece(PieceType.KING, Color.WHITE));
            map.put(new Position(Row.TWO, Column.F), new Piece(PieceType.ROOK, Color.WHITE));

            map.put(new Position(Row.EIGHT, Column.G), new Piece(PieceType.KNIGHT, Color.BLACK));
            map.put(new Position(Row.EIGHT, Column.F), new Piece(PieceType.BISHOP, Color.BLACK));
            map.put(new Position(Row.EIGHT, Column.E), new Piece(PieceType.KING, Color.BLACK));
            map.put(new Position(Row.SEVEN, Column.F), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
            map.put(new Position(Row.SEVEN, Column.D), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
            map.put(new Position(Row.SEVEN, Column.E), new Piece(PieceType.BLACK_PAWN, Color.BLACK));

            Board board = new Board(map);
            Map<Color, Double> scoreOfTeam = board.calculateScore();

            assertAll(
                    () -> assertEquals(scoreOfTeam.get(Color.WHITE), 15.5),
                    () -> assertEquals(scoreOfTeam.get(Color.BLACK), 8.5)
            );
        }
    }
}
