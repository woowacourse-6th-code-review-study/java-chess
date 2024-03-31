package dao;

import domain.piece.PieceColor;

public interface PieceColorDao {

    PieceColor find();

    void save(PieceColor playerColor);

    void update(PieceColor changeColor);

    void delete();
}
