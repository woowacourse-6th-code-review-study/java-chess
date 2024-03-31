package db;

import constant.ErrorCode;
import db.connection.DBConnectionUtil;
import db.dto.BoardDto;
import db.dto.PieceDto;
import db.dto.PositionDto;
import db.exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import model.Board;

public class BoardDao {

    private final String database;

    public BoardDao(final String database) {
        this.database = database;
    }

    public void saveBoard(final BoardDto board) {
        final Map<PositionDto, PieceDto> pieces = board.pieces();

        for (final Entry<PositionDto, PieceDto> entry : pieces.entrySet()) {
            savePosition(entry.getKey(), entry.getValue());
        }
    }

    private void savePosition(final PositionDto position, final PieceDto piece) {
        final String query = "INSERT INTO board VALUES(?, ?, ?)";
        try (final Connection connection = DBConnectionUtil.getConnection(database);
             final PreparedStatement preparedStatement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, position.value());
            preparedStatement.setString(2, piece.type());
            preparedStatement.setString(3, piece.camp());

            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            throw new DaoException(ErrorCode.FAIL_SAVE);
        }
    }

    public BoardDto find() {
        final String query = "SELECT * FROM board";

        try (final Connection connection = DBConnectionUtil.getConnection(database);
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            final Map<PositionDto, PieceDto> result = new HashMap<>();

            while (resultSet.next()) {
                final PositionDto position = new PositionDto(resultSet.getString("position"));
                final String type = resultSet.getString("piece_type");
                final String camp = resultSet.getString("camp");
                final PieceDto piece = new PieceDto(type, camp);
                result.put(position, piece);
            }
            if (result.isEmpty()) {
                return BoardDto.from(Board.create());
            }
            return new BoardDto(result);
        } catch (final SQLException exception) {
            throw new DaoException(ErrorCode.FAIL_FIND);
        }
    }

    public void remove() {
        final String query = "TRUNCATE TABLE board";
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
