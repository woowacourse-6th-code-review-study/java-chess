package chess.domain.piece;

import chess.domain.board.ChessBoard;
import chess.domain.position.Position;

public class Queen extends Piece {

    public static final int SCORE_VALUE = 9;

    public Queen(Team team) {
        super(team);
    }

    @Override
    boolean canNotMoveByItsOwnInPassing(Position start, Position destination) {
        return !start.isOrthogonalWith(destination) && !start.isDiagonalWith(destination);
    }

    @Override
    boolean canNotMoveByBoardStatus(Position start, Position destination, ChessBoard chessBoard) {
        return chessBoard.isPathHasObstacle(start.calculateSlidingPath(destination));
    }

    @Override
    Score score() {
        return new Score(SCORE_VALUE);
    }
}
