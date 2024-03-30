package db;

import db.connection.DBConnectionUtil;
import db.dto.MovingDto;
import java.sql.SQLException;
import java.sql.Statement;

public class MovingDao {

    private final String database;

    public MovingDao(final String database) {
        this.database = database;
    }

    public long addMoving(final MovingDto moving) {
        final var query = "INSERT INTO moving VALUES(?, ?, ?, ?, ?, ?)";
        try (final var connection = DBConnectionUtil.getConnection(database);
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            long autoIncrement = 0;
            preparedStatement.setNull(1, 0);
            preparedStatement.setString(2, moving.camp());
            preparedStatement.setString(3, moving.currentFile());
            preparedStatement.setString(4, moving.currentRank());
            preparedStatement.setString(5, moving.nextFile());
            preparedStatement.setString(6, moving.nextRank());

            preparedStatement.executeUpdate();
            final var generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                autoIncrement = generatedKeys.getLong(1);
            }
            return autoIncrement;
        } catch (final SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public MovingDto findByMovementId(final long movementId) {
        final var query = "SELECT * FROM moving WHERE movement_id = ?";
        try (final var connection = DBConnectionUtil.getConnection(database);
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, movementId);

            final var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new MovingDto(
                        resultSet.getString("camp"),
                        resultSet.getString("start_rank"),
                        resultSet.getString("start_file"),
                        resultSet.getString("destination_rank"),
                        resultSet.getString("destination_file")
                );
            }
        } catch (final SQLException exception) {
            throw new RuntimeException(exception);
        }
        return null;
    }

    public void remove() {
        final String query = "truncate table moving";
        try (final var connection = DBConnectionUtil.getConnection(database);
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
