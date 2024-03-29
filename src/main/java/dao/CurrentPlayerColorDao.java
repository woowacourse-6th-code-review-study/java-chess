package dao;

import domain.piece.PieceColor;

public interface CurrentPlayerColorDao {

    PieceColor findPlayerColor();

    void savePlayerColor(PieceColor playerColor);

    void updatePlayerColor(PieceColor changeColor);

    void deletePlayerColor();
}
