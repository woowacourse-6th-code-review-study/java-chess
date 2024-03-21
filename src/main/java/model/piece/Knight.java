package model.piece;

import java.util.Set;
import model.Camp;
import view.message.PieceType;
import model.position.Moving;
import model.position.Position;

public class Knight extends Piece {

    public Knight(final Camp camp) {
        super(camp);
    }

    @Override
    public Set<Position> getMoveRoute(final Moving moving) {
        if (canMovable(moving)) {
            return Set.of();
        }
        throw new IllegalArgumentException("이동 불가");
    }

    @Override
    protected boolean canMovable(final Moving moving) {
        final Position currentPosition = moving.getCurrentPosition();
        final Position nextPosition = moving.getNextPosition();

        if (moving.isNotMoved()) {
            return false;
        }
        final int dRow = Math.abs(currentPosition.getRowIndex() - nextPosition.getRowIndex());
        final int dColumn = Math.abs(currentPosition.getColumnIndex() - nextPosition.getColumnIndex());
        return dRow + dColumn == 3 && dRow != 0 && dColumn != 0;
    }

    @Override
    public String toString() {
        return PieceType.from(this).getValue();
    }
}