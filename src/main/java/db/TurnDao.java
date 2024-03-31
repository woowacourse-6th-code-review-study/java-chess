package db;

import constant.ErrorCode;
import db.connection.DBConnectionUtil;
import db.dto.TurnDto;
import db.exception.DaoException;
import java.sql.SQLException;
import java.sql.Statement;
import model.Camp;

public class TurnDao {

    private final String database;

    public TurnDao(final String database) {
        this.database = database;
    }

    public void saveTurn(final Camp camp) {
        final String query = "INSERT INTO turn values(?)";
        try (final var connection = DBConnectionUtil.getConnection(database);
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, camp.toString());
            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            throw new DaoException(ErrorCode.FAIL_SAVE);
        }
    }

    public TurnDto findTurn() {
        final String query = "SELECT * FROM turn";

        try (final var connection = DBConnectionUtil.getConnection(database);
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new TurnDto(resultSet.getString("camp"));
            }
            return null;

        } catch (SQLException exception) {
            throw new DaoException(ErrorCode.FAIL_FIND);
        }
    }

    public void remove() {
        final String query = "TRUNCATE TABLE turn";
        try (final var connection = DBConnectionUtil.getConnection(database);
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            throw new DaoException(ErrorCode.FAIL_DELETE);
        }
    }
}
