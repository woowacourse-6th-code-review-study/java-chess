package chess.domain.piece;

import chess.domain.board.ChessBoard;
import chess.domain.position.Position;

public class King extends Piece {
    private static final int MOVE_DISTANCE = 1;
    private static final int SCORE_VALUE = 0;

    public King(Team team) {
        super(team);
    }

    @Override
    boolean canNotMoveByItsOwnInPassing(Position start, Position destination) {
        return start.calculateDistance(destination) != MOVE_DISTANCE;
    }

    @Override
    boolean canNotMoveByBoardStatus(Position start, Position destination, ChessBoard chessBoard) {
        return false;
    }

    @Override
    public Score score() {
        return new Score(SCORE_VALUE);
    }
}
