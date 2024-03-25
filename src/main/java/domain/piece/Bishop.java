package domain.piece;

import domain.board.Position;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static domain.piece.CommonMovementDirection.UP_RIGHT;
import static domain.piece.CommonMovementDirection.UP_LEFT;
import static domain.piece.CommonMovementDirection.DOWN_RIGHT;
import static domain.piece.CommonMovementDirection.DOWN_LEFT;
import static domain.piece.CommonMovementDirection.calculateDirection;
import static domain.piece.PieceType.BISHOP;

public class Bishop extends Piece {
    private static final List<CommonMovementDirection> MOVABLE_DIRECTIONS = List.of(UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT);
    private static final PieceType PIECE_TYPE = BISHOP;

    public Bishop(final PieceColor color) {
        super(color);
    }

    @Override
    public void move(final Position source, final Position destination, final Map<Position, Piece> piecePositions) {
        CommonMovementDirection movementDirection = calculateDirection(source, destination);
        validateMovementDirection(movementDirection);

        List<Position> movePaths = Stream.iterate(source, current -> current.next(movementDirection))
                .takeWhile(current -> isContinuable(current, destination, piecePositions))
                .toList();

        Position alivePosition = movePaths.get(movePaths.size() - 1).next(movementDirection);
        checkAlivePosition(alivePosition, piecePositions);
    }

    private void validateMovementDirection(final CommonMovementDirection movementDirection) {
        if (!MOVABLE_DIRECTIONS.contains(movementDirection)) {
            throw new IllegalArgumentException("방향이 유효하지 않아 이동할 수 없는 칸입니다.");
        }
    }

    private boolean isContinuable(final Position current, final Position destination, final Map<Position, Piece> piecePositions) {
        if (current.equals(destination)) {
            return false;
        }

        if (piecePositions.containsKey(current)) {
            throw new IllegalArgumentException("목적지 경로에 기물이 존재하여 이동할 수 없습니다.");
        }

        return true;
    }

    private void checkAlivePosition(final Position alivePosition, final Map<Position, Piece> piecePositions) {
        if (piecePositions.containsKey(alivePosition) && !checkEnemy(piecePositions.get(alivePosition))) {
            throw new IllegalArgumentException("아군 기물이 위치한 칸으로는 이동할 수 없습니다.");
        }
    }

    @Override
    public PieceType pieceType() {
        return PIECE_TYPE;
    }
}
