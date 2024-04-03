package domain.piece;

import java.util.Arrays;
import java.util.function.Function;

public enum PieceType {
    PAWN(1, Pawn::new),
    ROOK(5, Rook::new),
    KNIGHT(2.5, Knight::new),
    BISHOP(3, Bishop::new),
    QUEEN(9, Queen::new),
    KING(0, King::new);

    private final double score;
    private final Function<PieceColor, Piece> convertPiece;

    PieceType(final double score, final Function<PieceColor, Piece> convertPiece) {
        this.score = score;
        this.convertPiece = convertPiece;
    }

    public static PieceType of(final String value) {
        return Arrays.stream(PieceType.values())
                .filter(pieceType -> pieceType.name().equals(value.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 PieceType 입니다."));
    }

    public double score() {
        return score;
    }

    public Piece createPiece(final PieceColor pieceColor) {
        return convertPiece.apply(pieceColor);
    }
}
