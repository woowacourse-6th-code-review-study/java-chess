package chess.dao;

import chess.domain.piece.Team;
import java.sql.Connection;
import java.util.Optional;

public interface TurnDAO {
    Optional<Team> select(Connection connection);

    boolean isNotEmpty(Connection connection);

    boolean save(Team team, Connection connection);

    boolean update(Team targetTeam, Team updatedTeam, Connection connection);

    void delete(Connection connection);
}
