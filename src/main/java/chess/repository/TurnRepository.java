package chess.repository;

import chess.domain.piece.Team;
import chess.repository.mapper.DomainMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class TurnRepository {
    private static final String TABLE_NAME = "turn";

    private final ConnectionManager connectionManager;

    public TurnRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void saveTurn(Team turn) {
        String query = String.format("INSERT INTO %s VALUES(?)", TABLE_NAME);

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, turn.name());
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Team> findCurrentTurn() {
        String query = String.format("SELECT * FROM %s WHERE ID = 1", TABLE_NAME);

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet resultSet = pstmt.executeQuery()) {

            if (resultSet.next()) {
                String value = resultSet.getString(1);
                return Optional.of(DomainMapper.mapToTurn(value));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void deleteAll() {
        String query = String.format("DELETE FROM %s", TABLE_NAME);

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
