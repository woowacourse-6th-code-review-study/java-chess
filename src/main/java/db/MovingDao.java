package db;

import db.dto.MovingDto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MovingDao {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    private final String database; // MySQL DATABASE 이름

    public MovingDao(final String database) {
        this.database = database;
    }

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + database + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public long addMoving(final MovingDto moving) {
        final var query = "INSERT INTO moving VALUES(?, ?, ?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MovingDto findByMovementId(final long movementId) {
        final var query = "SELECT * FROM moving WHERE movement_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, movementId);

            final var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new MovingDto(
                        resultSet.getString("camp"),
                        resultSet.getString("current"),
                        resultSet.getString("next")
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
