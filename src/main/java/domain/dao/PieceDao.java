package domain.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PieceDao {
    private final ConnectionManager connectionManager;

    private PieceDao(final ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public PieceDao() {
        this(new ConnectionManager());
    }

    public void add(final PieceDto piece) {
        final var query = "INSERT INTO piece VALUES(?, ?, ?, ?)";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, piece.boardFile());
            preparedStatement.setString(2, piece.boardRank());
            preparedStatement.setString(3, piece.color());
            preparedStatement.setString(4, piece.type());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PieceDto find(final String file, final String rank) {
        final var query = "SELECT * FROM piece WHERE board_file = ? and board_rank = ?";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, file);
            preparedStatement.setString(2, rank);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new PieceDto(file,
                        rank,
                        resultSet.getString("color"),
                        resultSet.getString("type")
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void deleteAll() {
        final var query = "DELETE FROM piece";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
