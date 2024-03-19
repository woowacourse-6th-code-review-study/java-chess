package domain;

import static domain.PieceMoveResult.FAILURE;
import static domain.PieceMoveResult.SUCCESS;

public abstract class AbstractPiece implements Piece {
    private final Team team;
    private Position position;

    public AbstractPiece(Position position, Team team) {
        this.position = position;
        this.team = team;
    }

    @Override
    public final PieceMoveResult move(Position targetPosition, PiecesOnChessBoard piecesOnChessBoard) {
        if (targetPosition == position) {
            return FAILURE;
        }
        PieceMoveResult pieceMoveResult = tryMove(targetPosition, piecesOnChessBoard);
        if (pieceMoveResult.equals(SUCCESS)) {
            position = targetPosition;
        }
        return pieceMoveResult;
    }

    public abstract PieceMoveResult tryMove(Position targetPosition, PiecesOnChessBoard piecesOnChessBoard);

    @Override
    public boolean isOn(Position position) {
        return this.position.equals(position);
    }

    @Override
    public Team getTeam() {
        return team;
    }

    protected Position getPosition() {
        return position;
    }
}
