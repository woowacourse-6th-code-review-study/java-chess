package chess.domain.piece;

import chess.domain.board.position.Direction;
import chess.domain.board.position.Position;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class Piece {

    private final PieceType pieceType;
    private final Color color;

    public Piece(PieceType pieceType, Color color) {
        this.pieceType = pieceType;
        this.color = color;
    }

    public Piece(String pieceType, String color) {
        this(PieceType.valueOf(pieceType), Color.valueOf(color));
    }

    public Map<Direction, Queue<Position>> generateAllDirectionPositions(Position currentPosition) {
        return pieceType.generateAllDirectionPositions(currentPosition);
    }

    public boolean isEnemy(Piece piece) {
        return isNotSameColor(piece.color);
    }

    public boolean isSameColor(Color color) {
        return this.color == color;
    }

    public boolean isNotSameColor(Color color) {
        return !isSameColor(color);
    }

    public boolean isKing() {
        return this.pieceType == PieceType.KING;
    }

    public double getScore() {
        return pieceType.getScore();
    }

    public Color getColor() {
        return color;
    }

    public boolean isPawnAttackPossible(Direction direction) {
        if (pieceType == PieceType.BLACK_PAWN) {
            return direction == Direction.SW || direction == Direction.SE;
        }
        if (pieceType == PieceType.WHITE_PAWN) {
            return direction == Direction.NW || direction == Direction.NE;
        }
        return true;
    }

    public boolean isPawnMovePossible(Direction direction) {
        if (pieceType == PieceType.BLACK_PAWN) {
            return direction == Direction.S;
        }
        if (pieceType == PieceType.WHITE_PAWN) {
            return direction == Direction.N;
        }
        return true;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return pieceType == piece.pieceType && color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, color);
    }
}
