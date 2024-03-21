package model.piece;

import java.util.HashSet;
import java.util.Set;
import model.Camp;
import model.PieceType;
import model.position.Column;
import model.position.Moving;
import model.position.Position;
import model.position.Row;

public class Bishop extends Piece {

    private static final int[] dRow = new int[]{1, 1, -1, -1};
    private static final int[] dColumn = new int[]{1, -1, 1, -1};


    public Bishop(final Camp camp) {
        super(camp);
    }

    @Override
    public Set<Position> getRoute(Moving moving) {

        if (!canMovable(moving)) {
            throw new IllegalArgumentException("이동 불가");
        }
        Position currentPosition = moving.currentPosition();
        Position nextPosition = moving.nextPosition();

        Set<Position> route = new HashSet<>();
        int currentRow = currentPosition.getRowIndex();
        int currentColumn = currentPosition.getColumnIndex();
        int nextRow = nextPosition.getRowIndex();
        int d = Math.abs(currentRow - nextRow);
        int index = findIndex(currentPosition, nextPosition);
        for (int i = 1; i < d; i++) {
            Row row = Row.from(currentRow + (i * dRow[index]));
            Column column = Column.from(currentColumn + (i * dColumn[index]));
            route.add(new Position(column, row));
        }
        return route;
    }

    private int findIndex(Position currentPosition, Position nextPosition) {
        int currentRow = currentPosition.getRowIndex();
        int currentColumn = currentPosition.getColumnIndex();

        int nextRow = nextPosition.getRowIndex();
        int nextColumn = nextPosition.getColumnIndex();

        if (currentRow < nextRow && currentColumn < nextColumn) {
            return 0;
        }
        if (currentRow < nextRow && currentColumn > nextColumn) {
            return 1;
        }
        if (currentRow > nextRow && currentColumn < nextColumn) {
            return 2;
        }
        if (currentRow > nextRow && currentColumn > nextColumn) {
            return 3;
        }
        //TODO 예외 고민
        throw new IllegalStateException("인덱스 에러");
    }

    @Override
    protected boolean canMovable(Moving moving) {
        if (moving.isNotMoved()) {
            return false;
        }
        return moving.isDiagonal();
    }

    @Override
    public String toString() {
        return PieceType.from(this).getValue();
    }
}
