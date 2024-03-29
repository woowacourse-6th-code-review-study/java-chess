package chess.dao;

import chess.domain.piece.Team;
import java.util.Optional;

public interface TurnDAO {
    Optional<Team> select();

    boolean save(Team team);

    boolean update(Team targetTeam, Team updatedTeam);

    boolean delete();
}
