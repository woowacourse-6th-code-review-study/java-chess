package chess.dao;

import chess.domain.piece.Team;

public interface TurnDAO {
    Team select();

    boolean save(Team team);

    boolean update(Team targetTeam, Team updatedTeam);
}
