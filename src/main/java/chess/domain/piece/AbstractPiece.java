package chess.domain.piece;

import chess.domain.Position;
import chess.domain.board.ChessBoard;

abstract class AbstractPiece implements Piece {
    private final Team team;
    private Position position;

    AbstractPiece(Position position, Team team) {
        this.position = position;
        this.team = team;
    }

    @Override
    public final PieceMoveResult move(Position targetPosition, ChessBoard chessBoard) {
        if (targetPosition == position) {
            return PieceMoveResult.FAILURE;
        }
        PieceMoveResult pieceMoveResult = tryMove(targetPosition, chessBoard);
        if (!pieceMoveResult.equals(PieceMoveResult.FAILURE)) {
            position = targetPosition;
        }
        return pieceMoveResult;
    }

    abstract PieceMoveResult tryMove(Position targetPosition, ChessBoard chessBoard);

    @Override
    public boolean isOn(Position position) {
        return this.position.equals(position);
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public int getColumn() {
        return position.getColumn();
    }

    @Override
    public int getRow() {
        return position.getRow();
    }

    @Override
    public boolean isTeamWith(Team team) {
        return this.team.equals(team);
    }

    protected Position getPosition() {
        return position;
    }

    @Override
    public int hashCode() {
        int result = team != null ? team.hashCode() : 0;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractPiece that = (AbstractPiece) o;

        if (team != that.team) {
            return false;
        }
        return position == that.position;
    }
}
