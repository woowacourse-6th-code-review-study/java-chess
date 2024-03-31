package db.dto;

import model.Camp;
import model.piece.Piece;

public record PieceDto(String type, String camp) {

    public static PieceDto from(final Piece piece) {
        return new PieceDto(PieceType.findValue(piece), piece.getCamp().name());
    }

    public Piece convert() {
        final Camp army = find();
        return PieceType.findValue(army, type);
    }

    private Camp find() {
        if ("BLACK".equals(camp)) {
            return Camp.BLACK;
        }
        return Camp.WHITE;
    }
}
