package db.dto;

import model.piece.Piece;

public record PieceDto(String type, String camp) {

    public static PieceDto from(final Piece piece) {
        final PieceType pieceType = PieceType.findValue(piece);
        final CampType campType = CampType.findByCamp(piece.getCamp());
        return new PieceDto(pieceType.getPieceName(), campType.getColorName());
    }

    public Piece convert() {
        final CampType army = CampType.findByColorName(camp);
        final PieceType pieceType = PieceType.findValue(army.getCamp(), type);
        return pieceType.getPiece();
    }
}
