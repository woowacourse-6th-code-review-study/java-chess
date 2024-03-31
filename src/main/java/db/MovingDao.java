package db;

import constant.ErrorCode;
import db.connection.DBConnectionUtil;
import db.dto.MovingDto;
import db.exception.DaoException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovingDao {

    private final String database;

    public MovingDao(final String database) {
        this.database = database;
    }

    public long addMoving(final MovingDto moving) {
        final var query = "INSERT INTO moving VALUES(?, ?, ?, ?)";
        try (final var connection = DBConnectionUtil.getConnection(database);
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            long autoIncrement = 0;
            preparedStatement.setNull(1, 0);
            preparedStatement.setString(2, moving.camp());
            preparedStatement.setString(3, moving.current());
            preparedStatement.setString(4, moving.next());

            preparedStatement.executeUpdate();
            final var generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                autoIncrement = generatedKeys.getLong(1);
            }
            return autoIncrement;
        } catch (final SQLException exception) {
            throw new DaoException(ErrorCode.FAIL_SAVE);
        }
    }

    public List<MovingDto> findAll() {
        final String query = "SELECT * FROM moving";
        final List<MovingDto> moving = new ArrayList<>();
        try (final var connection = DBConnectionUtil.getConnection(database);
             final var preparedStatement = connection.prepareStatement(query)) {

            final var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var camp = resultSet.getString("camp");
                var current = resultSet.getString("start");
                var next = resultSet.getString("destination");
                moving.add(new MovingDto(camp, current, next));
            }
            return moving;
        } catch (SQLException exception) {
            throw new DaoException(ErrorCode.FAIL_FIND);
        }
    }

    public int countMoving() {
        final String query = "SELECT count(*) AS count FROM moving";
        try (final var connection = DBConnectionUtil.getConnection(database);
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
            throw new DaoException(ErrorCode.FAIL_FIND);
        } catch (final SQLException exception) {
            throw new DaoException(ErrorCode.FAIL_FIND);
        }
    }

    public MovingDto findByMovementId(final long movementId) {
        final String query = "SELECT * FROM moving WHERE movement_id = ?";
        try (final var connection = DBConnectionUtil.getConnection(database);
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, movementId);
            final var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new MovingDto(
                        resultSet.getString("camp"),
                        resultSet.getString("start"),
                        resultSet.getString("destination")
                );
            }
            throw new DaoException(ErrorCode.FAIL_FIND);
        } catch (final SQLException exception) {
            throw new DaoException(ErrorCode.FAIL_FIND);
        }
    }

    public void remove() {
        final String query = "TRUNCATE TABLE moving";
        try (final var connection = DBConnectionUtil.getConnection(database);
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            throw new DaoException(ErrorCode.FAIL_DELETE);
        }
    }
}
