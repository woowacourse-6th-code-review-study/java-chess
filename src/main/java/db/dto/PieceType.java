package db.dto;

import java.util.Arrays;
import java.util.Objects;
import model.piece.Bishop;
import model.piece.BlackPawn;
import model.piece.King;
import model.piece.Knight;
import model.piece.Piece;
import model.piece.Queen;
import model.piece.Rook;
import model.piece.WhitePawn;

public enum PieceType {
    KING(King.class, "King"),
    QUEEN(Queen.class, "Queen"),
    ROOK(Rook.class, "Rook"),
    BISHOP(Bishop.class, "Bishop"),
    KNIGHT(Knight.class, "Knight"),
    WHITE_PAWN(WhitePawn.class, "Pawn"),
    BLACK_PAWN(BlackPawn.class, "Pawn");

    private final Class<? extends Piece> clazz;
    private final String value;

    PieceType(final Class<? extends Piece> clazz, final String value) {
        this.clazz = clazz;
        this.value = value;
    }

    public static String findValue(final Piece piece) {
        return Arrays.stream(values())
                .filter(pieceType -> Objects.equals(pieceType.clazz, piece.getClass()))
                .findFirst()
                .orElseThrow()
                .value;
    }
}
