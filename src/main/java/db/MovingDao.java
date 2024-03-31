package db;

import constant.ErrorCode;
import db.connection.DBConnectionUtil;
import db.dto.MovingDto;
import db.exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        final String query = "INSERT INTO moving VALUES(?, ?, ?, ?)";
        try (final Connection connection = DBConnectionUtil.getConnection(database);
             final PreparedStatement preparedStatement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)
        ) {
            long autoIncrement = 0;
            preparedStatement.setNull(1, 0);
            preparedStatement.setString(2, moving.camp());
            preparedStatement.setString(3, moving.current());
            preparedStatement.setString(4, moving.next());

            preparedStatement.executeUpdate();
            final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

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
        try (final Connection connection = DBConnectionUtil.getConnection(database);
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String camp = resultSet.getString("camp");
                String current = resultSet.getString("start");
                String next = resultSet.getString("destination");
                moving.add(new MovingDto(camp, current, next));
            }
            return moving;
        } catch (SQLException exception) {
            throw new DaoException(ErrorCode.FAIL_FIND);
        }
    }

    public int countMoving() {
        final String query = "SELECT count(*) AS count FROM moving";
        try (final Connection connection = DBConnectionUtil.getConnection(database);
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
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
        try (final Connection connection = DBConnectionUtil.getConnection(database);
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, movementId);
            final ResultSet resultSet = preparedStatement.executeQuery();

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
        try (final Connection connection = DBConnectionUtil.getConnection(database);
             final PreparedStatement preparedStatement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            throw new DaoException(ErrorCode.FAIL_DELETE);
        }
    }
}
