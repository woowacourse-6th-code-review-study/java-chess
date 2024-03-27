package chess.domain.piece;

import chess.domain.board.ChessBoard;
import chess.domain.position.Position;

public class Bishop extends Piece {
    private static final int SCORE_VALUE = 3;

    public Bishop(Team team) {
        super(team);
    }

    @Override
    boolean canNotMoveByItsOwnInPassing(Position start, Position destination) {
        return !start.isDiagonalWith(destination);
    }

    @Override
    boolean canNotMoveByBoardStatus(Position start, Position destination, ChessBoard chessBoard) {
        return chessBoard.isPathHasObstacle(start.calculateSlidingPath(destination));
    }

    @Override
    public Score score() {
        return new Score(SCORE_VALUE);
    }

    @Override
    public boolean isPawn() {
        return false;
    }
}
