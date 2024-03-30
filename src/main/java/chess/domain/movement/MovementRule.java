package chess.domain.movement;

import chess.domain.position.Position;
import java.util.List;

public interface MovementRule {

    boolean isMovable(Position start, Position end, boolean hasEnemy);

    List<Position> findPath(Position start, Position end, boolean hasEnemy);
}