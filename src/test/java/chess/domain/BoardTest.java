package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.piece.Bishop;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.piece.Team;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("특정 위치에 어떤 말이 있는지 알려준다.")
    void findTest_whenPieceExist() {
        Position position = new Position(File.D, Rank.TWO);
        King king = new King(Team.WHITE);
        Map<Position, Piece> map = Map.of(position, king);
        Board board = new Board(map);

        assertThat(board.find(position)).isEqualTo(Optional.of(king));
    }

    @Test
    @DisplayName("특정 위치에 어떤 말이 있는지 알려준다.")
    void findTest_whenPieceNotExist() {
        Position position = new Position(File.E, Rank.TWO);
        King king = new King(Team.WHITE);
        Map<Position, Piece> map = Map.of(position, king);
        Position notExistPosition = new Position(File.D, Rank.TWO);
        Board board = new Board(map);

        assertThat(board.find(notExistPosition)).isEqualTo(Optional.empty());
    }

    @Nested
    @DisplayName("말 이동 테스트")
    class MovingTest {

        private static final Position START_KING = new Position(File.E, Rank.FOUR);
        private static final King KING = new King(Team.WHITE);
        private static final Position START_QUEEN = new Position(File.E, Rank.TWO);
        private static final Queen QUEEN = new Queen(Team.WHITE);
        private static final Map<Position, Piece> MAP = Map.of(START_KING, KING, START_QUEEN, QUEEN);
        private Board board;

        @BeforeEach
        void beforeEach() {
            board = new Board(MAP);
        }

        @Test
        @DisplayName("시작 위치에 있는 말을 도착 위치로 움직인다.")
        void moveTest() {
            Position possibleEnd = new Position(File.E, Rank.THREE);

            board.tryMove(START_KING, possibleEnd);

            assertAll(
                    () -> assertThat(board.find(possibleEnd)).isEqualTo(Optional.of(KING)),
                    () -> assertThat(board.find(START_KING)).isEqualTo(Optional.empty())
            );
        }

        @Test
        @DisplayName("시작 위치에 말이 없을 경우, 예외가 발생한다.")
        void moveTest_whenPieceNotExist_throwException() {
            Position emptyPosition = new Position(File.F, Rank.EIGHT);
            Position end = new Position(File.F, Rank.TWO);

            assertThatThrownBy(() -> board.tryMove(emptyPosition, end))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 위치에 말이 없습니다.");
        }

        @Test
        @DisplayName("말의 이동 반경을 벗어날 경우, 예외가 발생한다.")
        void moveTest_whenOutOfMovement_throwException() {
            Position impossibleEnd = new Position(File.F, Rank.EIGHT);

            assertThatThrownBy(() -> board.tryMove(START_KING, impossibleEnd))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("불가능한 경로입니다.");
        }

        @Test
        @DisplayName("이동 경로 위에 다른 말이 존재할 경우, 예외가 발생한다.")
        void moveTest_whenBlocked_throwException() {
            Position impossibleEnd = new Position(File.E, Rank.FIVE);

            assertThatThrownBy(() -> board.tryMove(START_QUEEN, impossibleEnd))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("다른 말이 있어 이동 불가능합니다.");
        }
    }

    @Test
    @DisplayName("특정 팀의 말들을 알 수 있다.")
    void getPiecesOfTest() {
        Queen queen = new Queen(Team.WHITE);
        Rook rook = new Rook(Team.WHITE);
        Bishop bishop = new Bishop(Team.WHITE);
        Knight knight = new Knight(Team.BLACK);
        Pawn pawn = new Pawn(Team.BLACK);
        King king = new King(Team.BLACK);
        Map<Position, Piece> boardMap = Map.of(
                new Position(File.B, Rank.TWO), queen,
                new Position(File.F, Rank.TWO), rook,
                new Position(File.F, Rank.SIX), bishop,
                new Position(File.D, Rank.THREE), knight,
                new Position(File.E, Rank.SIX), pawn,
                new Position(File.A, Rank.FOUR), king);
        Board board = new Board(boardMap);

        assertAll(
                () -> assertThat(board.getPiecesOf(Team.WHITE).toList())
                        .containsExactlyInAnyOrder(queen, rook, bishop),
                () -> assertThat(board.getPiecesOf(Team.BLACK).toList())
                        .containsExactlyInAnyOrder(knight, pawn, king)
        );
    }

    @Test
    @DisplayName("특정 팀의 폰의 위치들을 알 수 있다.")
    void getPawnPositionsOfTest() {
        Map<Position, Piece> boardMap = new java.util.HashMap<>(Map.of(
                new Position(File.B, Rank.EIGHT), new King(Team.BLACK),
                new Position(File.C, Rank.EIGHT), new Rook(Team.BLACK),
                new Position(File.A, Rank.SEVEN), new Pawn(Team.BLACK),
                new Position(File.C, Rank.SEVEN), new Pawn(Team.BLACK),
                new Position(File.D, Rank.SEVEN), new Bishop(Team.BLACK),
                new Position(File.B, Rank.SIX), new Pawn(Team.BLACK),
                new Position(File.E, Rank.SIX), new Queen(Team.BLACK)
        ));
        Map<Position, Piece> addedBoard = Map.of(
                new Position(File.F, Rank.FOUR), new Knight(Team.WHITE),
                new Position(File.G, Rank.FOUR), new Queen(Team.WHITE),
                new Position(File.F, Rank.THREE), new Pawn(Team.WHITE),
                new Position(File.H, Rank.THREE), new Pawn(Team.WHITE),
                new Position(File.F, Rank.TWO), new Pawn(Team.WHITE),
                new Position(File.G, Rank.TWO), new Pawn(Team.WHITE),
                new Position(File.E, Rank.ONE), new Rook(Team.WHITE),
                new Position(File.F, Rank.ONE), new King(Team.WHITE)
        );
        boardMap.putAll(addedBoard);
        Board board = new Board(boardMap);

        assertAll(
                () -> assertThat(board.getPawnPositionsOf(Team.BLACK).toList())
                        .containsExactlyInAnyOrder(
                                new Position(File.A, Rank.SEVEN),
                                new Position(File.C, Rank.SEVEN),
                                new Position(File.B, Rank.SIX)),
                () -> assertThat(board.getPawnPositionsOf(Team.WHITE).toList())
                        .containsExactlyInAnyOrder(
                                new Position(File.F, Rank.THREE),
                                new Position(File.H, Rank.THREE),
                                new Position(File.F, Rank.TWO),
                                new Position(File.G, Rank.TWO)
                        )
        );
    }
}
