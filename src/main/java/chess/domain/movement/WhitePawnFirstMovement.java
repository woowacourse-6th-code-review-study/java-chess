package chess.domain.movement;

import chess.domain.position.Position;
import chess.domain.position.Rank;
import java.util.List;

public final class WhitePawnFirstMovement implements MovementRule {

    private static final Rank WHITE_PAWN_INIT_RANK = Rank.TWO;
    private static final int TO_NORTH_TWO_BLOCK = 2;
    private static final int SAME_FILE = 0;

    @Override
    public boolean isMovable(Position start, Position end, boolean isAttack) {
        int rankDifference = start.calculateRankDifference(end);
        int fileDifference = start.calculateFileDifference(end);

        boolean isEmptyAtEnd = !isAttack;
        boolean isExistInitPosition = start.isSameRank(WHITE_PAWN_INIT_RANK);
        boolean isMatchDifference = (rankDifference == TO_NORTH_TWO_BLOCK && fileDifference == SAME_FILE);

        return isEmptyAtEnd && isExistInitPosition && isMatchDifference;
    }

    @Override
    public List<Position> findPath(Position start, Position end, boolean isAttack) {
        if (!isMovable(start, end, isAttack)) {
            throw new IllegalArgumentException("경로가 존재하지 않습니다.");
        }

        Position middle = start.moveToNorth();
        return List.of(middle, end);
    }
}
