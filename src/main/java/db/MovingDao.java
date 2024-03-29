package db;

import db.dto.BoardDto;
import db.dto.MovingDto;
import db.dto.PieceDto;
import db.dto.PositionDto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

    public void addBoard(final BoardDto board) {
        final Map<PositionDto, PieceDto> pieces = board.pieces();

        for (final Entry<PositionDto, PieceDto> entry : pieces.entrySet()) {
            addPosition(entry.getKey(), entry.getValue());
        }
    }

    private void addPosition(final PositionDto position, final PieceDto piece) {
        final var query = "INSERT INTO board VALUES(?, ?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, position.value());
            preparedStatement.setString(2, piece.type());
            preparedStatement.setString(3, piece.camp());

            preparedStatement.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public long addMoving(final MovingDto moving) {
        final var query = "INSERT INTO moving VALUES(?, ?, ?, ?, ?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
                        resultSet.getString("start_rank"),
                        resultSet.getString("start_file"),
                        resultSet.getString("destination_rank"),
                        resultSet.getString("destination_file")
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public BoardDto findBoard() {
        final var query = "SELECT * FROM board";

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            final var resultSet = preparedStatement.executeQuery();

            final Map<PositionDto, PieceDto> result = new HashMap<>();

            while (resultSet.next()) {
                final var position = new PositionDto(resultSet.getString("position"));
                final var type = resultSet.getString("piece_type");
                final var camp = resultSet.getString("camp");
                final var piece = new PieceDto(type, camp);

                result.put(position, piece);

            }
            return new BoardDto(result);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createMoving() {
        final var query = """
                create table moving
                 (
                     movement_id      INT primary key auto_increment,
                     camp             varchar(5)  not null,
                     start_rank       varchar(12) not null,
                     start_file       varchar(12) not null,
                     destination_rank varchar(12) not null,
                     destination_file varchar(12) not null
                 )""";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createBoard() {
        final var query = """
                create table board
                (
                    position   varchar(2)  not null,
                    piece_type varchar(6) not null,
                    camp       varchar(5)  not null

                )""";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(String table) {
        final var query = String.format("drop table %s", table);
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
