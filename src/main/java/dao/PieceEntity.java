package dao;

import domain.board.File;
import domain.board.Rank;
import domain.piece.PieceColor;
import domain.piece.PieceType;

public record PieceEntity(PieceType pieceType, PieceColor pieceColor, File file, Rank rank) {
}
