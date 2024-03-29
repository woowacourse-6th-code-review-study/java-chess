package db.dto;

import model.piece.Piece;

public record PieceDto(String type, String camp) {

    public static PieceDto from(final Piece piece) {
        return new PieceDto(PieceType.findValue(piece), piece.getCamp().toString());
    }
}
