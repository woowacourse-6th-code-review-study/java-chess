package db.dto;

import model.Camp;
import model.piece.Bishop;
import model.piece.BlackPawn;
import model.piece.King;
import model.piece.Knight;
import model.piece.Piece;
import model.piece.Queen;
import model.piece.Rook;
import model.piece.WhitePawn;

public record PieceDto(String type, String camp) {

    public static PieceDto from(final Piece piece) {
        return new PieceDto(PieceType.findValue(piece), piece.getCamp().toString());
    }

    //TODO 요고 리팩터링
    public Piece convert() {
        final Camp army = find();
        if ("King".equals(type)) {
            return new King(army);
        }
        if ("Queen".equals(type)) {
            return new Queen(army);
        }
        if ("Knight".equals(type)) {
            return new Knight(army);
        }
        if ("Bishop".equals(type)) {
            return new Bishop(army);
        }
        if ("Rook".equals(type)) {
            return new Rook(army);
        }
        if ("Pawn".equals(type) && army == Camp.WHITE) {
            return new WhitePawn();
        }
        return new BlackPawn();
    }

    private Camp find() {
        if ("BLACK".equals(camp)) {
            return Camp.BLACK;
        }
        return Camp.WHITE;
    }
}
