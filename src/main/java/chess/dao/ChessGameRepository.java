package chess.dao;

import chess.domain.Team;

public interface ChessGameRepository {

    boolean isExistGame();

    void update(Team turn);

    void deleteAll();
}
