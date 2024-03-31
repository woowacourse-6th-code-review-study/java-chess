package chess.domain.positionFilter;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.position.Column;
import chess.domain.board.position.Direction;
import chess.domain.board.position.Position;
import chess.domain.board.position.Row;
import chess.domain.game.PositionsFilter;
import chess.domain.mock.BlackPieceRepository;
import chess.domain.mock.NotExistsPieceRepository;
import chess.domain.mock.WhitePieceRepository;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KingTest {

    @Test
    @DisplayName("해당 포지션이 비어있을 시 이동 가능한 포지션에 모두 포함되어야 한다.")
    void startPositionPawnWithOnlyAttackPossiblePositions() {
        Piece piece = new Piece(PieceType.KING, Color.WHITE);
        Position position = new Position(Row.FIVE, Column.D);
        Map<Direction, Queue<Position>> candidateAllPositions = piece.generateAllDirectionPositions(position);
        PositionsFilter positionsFilter = new PositionsFilter(new NotExistsPieceRepository(),
                candidateAllPositions);

        List<Position> movablePositions = positionsFilter.generateValidPositions(piece);

        assertThat(movablePositions).containsExactlyInAnyOrder(
                new Position(Row.SIX, Column.D),
                new Position(Row.SIX, Column.E),
                new Position(Row.FIVE, Column.E),
                new Position(Row.FOUR, Column.E),
                new Position(Row.FOUR, Column.D),
                new Position(Row.FOUR, Column.C),
                new Position(Row.FIVE, Column.C),
                new Position(Row.SIX, Column.C)
        );
    }

    @Test
    @DisplayName("해당 포지션에 상대 기물이 존재하면 이동할 수 있는 위치에 포함되어야 한다.")
    void startPositionPawnWithFreePositions() {
        Piece piece = new Piece(PieceType.KING, Color.WHITE);
        Position position = new Position(Row.FIVE, Column.D);
        Map<Direction, Queue<Position>> candidateAllPositions = piece.generateAllDirectionPositions(position);
        PositionsFilter positionsFilter = new PositionsFilter(new BlackPieceRepository(),
                candidateAllPositions);

        List<Position> movablePositions = positionsFilter.generateValidPositions(piece);

        assertThat(movablePositions).containsExactlyInAnyOrder(
                new Position(Row.SIX, Column.D),
                new Position(Row.SIX, Column.E),
                new Position(Row.FIVE, Column.E),
                new Position(Row.FOUR, Column.E),
                new Position(Row.FOUR, Column.D),
                new Position(Row.FOUR, Column.C),
                new Position(Row.FIVE, Column.C),
                new Position(Row.SIX, Column.C)
        );
    }

    @Test
    @DisplayName("해당 포지션이 우리팀 기물일 시 이동가능한 위치에 포함되어서는 안된다.")
    void startPositionPawnWithCantMovePositions() {
        Piece piece = new Piece(PieceType.KING, Color.WHITE);
        Position position = new Position(Row.FIVE, Column.D);
        Map<Direction, Queue<Position>> candidateAllPositions = piece.generateAllDirectionPositions(position);
        PositionsFilter positionsFilter = new PositionsFilter(new WhitePieceRepository(),
                candidateAllPositions);

        List<Position> movablePositions = positionsFilter.generateValidPositions(piece);

        assertThat(movablePositions).isEmpty();
    }
}
