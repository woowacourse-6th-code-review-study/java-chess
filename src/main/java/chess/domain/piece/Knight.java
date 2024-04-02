package chess.domain.piece;

import chess.domain.Position;
import chess.domain.board.ChessBoard;
import java.util.Optional;

public final class Knight extends AbstractCatchOnMovePiece {

    public Knight(Position position, Team team) {
        super(position, team);
    }

    @Override
    public Optional<PieceMoveResult> tryMoveAssumeAloneAndCheckRoute(Position targetPosition, ChessBoard chessBoard) {
        if (!isMovablePosition(targetPosition)) {
            return Optional.of(PieceMoveResult.FAILURE);
        }
        return Optional.empty();
    }

    private boolean isMovablePosition(Position targetPosition) {
        Position nowPosition = getPosition();
        int absRowDistance = Math.abs(nowPosition.rowDistance(targetPosition));
        int absColDistance = Math.abs(nowPosition.columnDistance(targetPosition));
        return (absRowDistance == 2 && absColDistance == 1) || (absRowDistance == 1 && absColDistance == 2);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public double getPoint() {
        return 2.5;
    }
}
