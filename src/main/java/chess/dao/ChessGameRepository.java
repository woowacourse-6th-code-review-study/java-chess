package chess.dao;

import chess.dto.TurnType;

public interface ChessGameRepository {

    boolean isExistGame();

    void update(TurnType turn);

    void deleteAll();
}
