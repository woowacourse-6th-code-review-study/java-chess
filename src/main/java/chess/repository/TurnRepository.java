package chess.repository;

import chess.domain.piece.Team;
import chess.repository.mapper.DomainMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TurnRepository {
    private static final String TABLE_NAME = "turn";

    private final ConnectionManager connectionManager;

    public TurnRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void saveTurn(Team turn) throws SQLException {
        String query = String.format("INSERT INTO %s VALUES(?", TABLE_NAME);

        Connection connection = connectionManager.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, turn.name());
        pstmt.execute();
    }

    public Team findCurrentTurn() throws SQLException {
        String query = String.format("SELECT * from %s WHERE ID = 1", TABLE_NAME);

        Connection connection = connectionManager.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);

        ResultSet resultSet = pstmt.executeQuery();
        String value = resultSet.getString(1);

        return DomainMapper.mapToTurn(value);
    }

    public void deleteAll() throws SQLException {
        String query = String.format("DELETE FROM %s", TABLE_NAME);

        Connection connection = connectionManager.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.executeUpdate();
    }
}
