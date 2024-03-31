package db.dto;

import java.util.Arrays;
import model.Camp;
import model.piece.Bishop;
import model.piece.BlackPawn;
import model.piece.King;
import model.piece.Knight;
import model.piece.Piece;
import model.piece.Queen;
import model.piece.Rook;
import model.piece.WhitePawn;

public enum PieceType {

    WHITE_KING(new King(Camp.WHITE), "King", Camp.WHITE),
    WHITE_QUEEN(new Queen(Camp.WHITE), "Queen", Camp.WHITE),
    WHITE_ROOK(new Rook(Camp.WHITE), "Rook", Camp.WHITE),
    WHITE_BISHOP(new Bishop(Camp.WHITE), "Bishop", Camp.WHITE),
    WHITE_KNIGHT(new Knight(Camp.WHITE), "Knight", Camp.WHITE),
    WHITE_PAWN(new WhitePawn(), "Pawn", Camp.WHITE),
    BLACK_KING(new King(Camp.BLACK), "King", Camp.BLACK),
    BLACK_QUEEN(new Queen(Camp.BLACK), "Queen", Camp.BLACK),
    BLACK_ROOK(new Rook(Camp.BLACK), "Rook", Camp.BLACK),
    BLACK_BISHOP(new Bishop(Camp.BLACK), "Bishop", Camp.BLACK),
    BLACK_KNIGHT(new Knight(Camp.BLACK), "Knight", Camp.BLACK),
    BLACK_PAWN(new BlackPawn(), "Pawn", Camp.BLACK);

    private final Piece piece;
    private final String pieceName;
    private final Camp camp;

    PieceType(final Piece piece, final String pieceName, final Camp camp) {
        this.piece = piece;
        this.pieceName = pieceName;
        this.camp = camp;
    }

    public static String findValue(final Piece piece) {
        return Arrays.stream(values())
                .filter(pieceType1 -> pieceType1.piece.getClass().isInstance(piece))
                .findFirst()
                .orElseThrow()
                .pieceName;
    }

    public static Piece findValue(final Camp camp, final String type) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.pieceName.equals(type))
                .filter(pieceType -> pieceType.camp == camp)
                .findFirst()
                .orElseThrow()
                .piece;
    }
}
