package chess.dao;

import chess.domain.piece.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TurnDAOForMysql implements TurnDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:13306/chess?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER_NAME = "user";
    private static final String DB_USER_PASSWORD = "password";
    private final DBConnectionCache dbConnectionCache;

    public TurnDAOForMysql() {
        this(DB_URL, DB_USER_NAME, DB_USER_PASSWORD);
    }

    private TurnDAOForMysql(String dbUrl, String userName, String password) {
        this.dbConnectionCache = new DBConnectionCache(dbUrl, userName, password);
    }

    @Override
    public Optional<Team> select() {
        Connection connection = dbConnectionCache.getConnection();
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
    public boolean save(Team team) {
        try {
            Connection connection = dbConnectionCache.getConnection();
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
    public boolean update(Team targetTeam, Team updatedTeam) {
        try {
            Connection connection = dbConnectionCache.getConnection();
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
    public void delete() {
        try {
            Connection connection = dbConnectionCache.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from game");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
