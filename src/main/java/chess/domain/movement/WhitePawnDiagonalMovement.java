package chess.domain.movement;

import chess.domain.position.Position;
import java.util.List;

public final class WhitePawnDiagonalMovement implements MovementRule {

    private static final int TO_NORTH_ONE_BLOCK = 1;
    private static final int ONE_BLOCK = 1;

    @Override
    public boolean isMovable(Position start, Position end, boolean isAttack) {
        int rankDifference = start.calculateRankDifference(end);
        int fileDifference = start.calculateFileDifference(end);

        boolean isMatchDifference = (rankDifference == TO_NORTH_ONE_BLOCK && Math.abs(fileDifference) == ONE_BLOCK);
        return isAttack && isMatchDifference;
    }

    @Override
    public List<Position> findPath(Position start, Position end, boolean isAttack) {
        if (!isMovable(start, end, isAttack)) {
            throw new IllegalArgumentException("경로가 존재하지 않습니다.");
        }

        return List.of(end);
    }
}
