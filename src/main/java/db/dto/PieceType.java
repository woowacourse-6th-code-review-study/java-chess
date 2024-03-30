package db.dto;

import java.util.Arrays;
import model.piece.Bishop;
import model.piece.King;
import model.piece.Knight;
import model.piece.Pawn;
import model.piece.Piece;
import model.piece.Queen;
import model.piece.Rook;

public enum PieceType {
    KING(King.class, "King"),
    QUEEN(Queen.class, "Queen"),
    ROOK(Rook.class, "Rook"),
    BISHOP(Bishop.class, "Bishop"),
    KNIGHT(Knight.class, "Knight"),
    PAWN(Pawn.class, "Pawn");

    private final Class<? extends Piece> clazz;
    private final String value;

    PieceType(final Class<? extends Piece> clazz, final String value) {
        this.clazz = clazz;
        this.value = value;
    }

    public static String findValue(final Piece piece) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.clazz.isInstance(piece))
                .findFirst()
                .orElseThrow()
                .value;
    }
}
