package piece;

import java.util.HashSet;
import java.util.Set;
import model.Camp;
import point.Column;
import point.Position;
import point.Row;

public class Bishop extends Piece {

    private static final int[] dRow = new int[]{1, 1, -1, -1};
    private static final int[] dColumn = new int[]{1, -1, 1, -1};


    public Bishop(final Camp camp) {
        super(camp);
    }

    @Override
    public void move(Position targetPosition) {

    }

    @Override
    public Set<Position> getRoute(Position currentPosition, Position nextPosition) {

        if (canMovable(currentPosition, nextPosition)) {
            Set<Position> route = new HashSet<>();

            int currentRow = currentPosition.getRowIndex();
            int currentColumn = currentPosition.getColumnIndex();

            int nextRow = nextPosition.getRowIndex();

            int d = Math.abs(currentRow - nextRow);

            int index = findIndex(currentPosition, nextPosition);

            for (int i = 1; i < d; i++) {
                Row row = Row.from(currentRow + (i * dRow[index]));
                Column column = Column.from(currentColumn + (i * dColumn[index]));
                route.add(new Position(row, column));
            }
            return route;
        }
        throw new IllegalArgumentException("이동 불가");
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
    protected boolean canMovable(Position currentPosition, Position nextPosition) {
        if (currentPosition.equals(nextPosition)) {
            return false;
        }

        int currentRow = currentPosition.getRowIndex();
        int currentColumn = currentPosition.getColumnIndex();

        int nextRow = nextPosition.getRowIndex();
        int nextColumn = nextPosition.getColumnIndex();

        return Math.abs(currentRow - nextRow) == Math.abs(currentColumn - nextColumn);
    }

    @Override
    public String toString() {
        if (camp == Camp.WHITE) {
            return "b";
        }
        return "B";
    }
}