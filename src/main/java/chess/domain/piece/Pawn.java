package chess.domain.piece;

import chess.domain.board.ChessBoard;
import chess.domain.position.Direction;
import chess.domain.position.Position;

public class Pawn extends Piece {
    private static final int DEFAULT_MOVE_DISTANCE = 1;
    private static final int INITIAL_MOVE_DISTANCE = 2;

    public Pawn(Team team) {
        super(team);
    }


    @Override
    boolean canNotMoveByItsOwnInPassing(Position start, Position destination) {
        int distance = start.calculateDistance(destination);
        if (!team.isTeamForwardDirectionsContains(Direction.of(start, destination))) {
            return true;
        }
        if (distance == DEFAULT_MOVE_DISTANCE && start.isOrthogonalWith(destination)) {
            return false;
        }
        if (distance == INITIAL_MOVE_DISTANCE && start.isOrthogonalWith(destination)) {
            return false;
        }
        if (distance == DEFAULT_MOVE_DISTANCE && start.isDiagonalWith(destination)) {
            return false;
        }
        return true;
    }

    @Override
    boolean canNotMoveByBoardStatus(Position start, Position destination, ChessBoard chessBoard) {
        int distance = start.calculateDistance(destination);
        if (chessBoard.isPathHasObstacle(start.calculateSlidingPath(destination))) {
            return true;
        }
        if (!chessBoard.positionIsEmpty(destination) && start.isOrthogonalWith(destination)) {
            return true;
        }
        if (distance == INITIAL_MOVE_DISTANCE) {
            return !team.isPositionOnTeamInitialPawnRank(start);
        }
        if (distance == DEFAULT_MOVE_DISTANCE && start.isDiagonalWith(destination)) {
            return chessBoard.isNoHostilePieceAt(destination, team);
        }
        return false;
    }
}
