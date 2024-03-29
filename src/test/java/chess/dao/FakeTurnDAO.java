package chess.dao;

import chess.domain.piece.Team;
import java.util.Optional;

public class FakeTurnDAO implements TurnDAO {
    private Team team;

    @Override
    public Optional<Team> select() {
        return Optional.ofNullable(team);
    }

    @Override
    public boolean save(Team team) {
        if (this.team == null) {
            this.team = team;
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Team targetTeam, Team updatedTeam) {
        if (targetTeam.equals(team)) {
            team = updatedTeam;
            return true;
        }
        return false;
    }

    @Override
    public void delete() {
        team = null;
    }
}
