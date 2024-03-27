package chess.domain.piece;

import chess.domain.board.ChessBoard;
import chess.domain.position.Position;

public class Rook extends Piece {

    public static final int SCORE_VALUE = 5;

    public Rook(Team team) {
        super(team);
    }

    @Override
    boolean canNotMoveByItsOwnInPassing(Position start, Position destination) {
        return !start.isOrthogonalWith(destination);
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
