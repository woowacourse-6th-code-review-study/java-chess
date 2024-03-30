package chess.dao;

import chess.domain.piece.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TurnDAOForMysql implements TurnDAO {

    @Override
    public Optional<Team> select(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select current_team_name from game");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (!(resultSet.isFirst() && resultSet.isLast())) {
                return Optional.empty();
            }
            String teamName = resultSet.getString(1);
            return Optional.of(Team.valueOf(teamName));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(Team team, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO game (current_team_name) "
                            + "SELECT ? "
                            + "FROM dual "
                            + "WHERE NOT EXISTS (SELECT * FROM game)");
            preparedStatement.setString(1, team.name());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Team targetTeam, Team updatedTeam, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update game set current_team_name = ? where current_team_name = ?");
            preparedStatement.setString(1, updatedTeam.name());
            preparedStatement.setString(2, targetTeam.name());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from game");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
