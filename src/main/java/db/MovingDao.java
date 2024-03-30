package db;

import db.connection.DBConnectionUtil;
import db.dto.BoardDto;
import db.dto.MovingDto;
import db.dto.PieceDto;
import db.dto.PositionDto;
import db.dto.TurnDto;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import model.Camp;

public class MovingDao { // TODO 이름 수정

    private final String database;

    public MovingDao(final String database) {
        this.database = database;
    }

    public void addBoard(final BoardDto board) {
        final Map<PositionDto, PieceDto> pieces = board.pieces();

        for (final Entry<PositionDto, PieceDto> entry : pieces.entrySet()) {
            addPosition(entry.getKey(), entry.getValue());
        }
    }

    private void addPosition(final PositionDto position, final PieceDto piece) {
        final var query = "INSERT INTO board VALUES(?, ?, ?)";
        try (final var connection = DBConnectionUtil.getConnection(database);
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, position.value());
            preparedStatement.setString(2, piece.type());
            preparedStatement.setString(3, piece.camp());

            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            throw new RuntimeException(exception);
        }

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

    public BoardDto findBoard() {
        final var query = "SELECT * FROM board";

        try (final var connection = DBConnectionUtil.getConnection(database);
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
        } catch (final SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void remove(String table) {
        final String query = String.format("truncate table %s", table);
        try (final var connection = DBConnectionUtil.getConnection(database);
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            throw new RuntimeException(exception);
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
            throw new RuntimeException(exception);
        }
    }

    public void addTurn(final Camp camp) {
        final String query = "INSERT INTO turn values(?)";
        try (final var connection = DBConnectionUtil.getConnection(database);
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, camp.toString());
            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
