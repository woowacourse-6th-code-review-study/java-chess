package repository;

import db.JdbcTemplate;
import db.RowMapper;
import dto.PieceDto;
import dto.RoomDto;

import java.util.List;

public class PieceDao {
    private static final String TABLE_NAME = "pieces";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<PieceDto> rowMapper = (resultSet) -> new PieceDto(
            resultSet.getString("board_file"),
            resultSet.getString("board_rank"),
            resultSet.getString("color"),
            resultSet.getString("type")
    );

    PieceDao() {
        this(new JdbcTemplate());
    }

    PieceDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void add(final RoomDto room, final PieceDto piece) {
        final String query = "INSERT INTO " + TABLE_NAME + " VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.execute(query,
                String.valueOf(room.room_id()), piece.boardFile(), piece.boardRank(),
                piece.color(), piece.type());
    }

    PieceDto findOne(final String file, final String rank) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE board_file = ? and board_rank = ? limit 1";
        final List<PieceDto> pieces = jdbcTemplate.executeAndGet(query, rowMapper, file, rank);
        if (pieces.isEmpty()) {
            throw new IllegalArgumentException("데이터가 없습니다.");
        }
        return pieces.get(0);
    }

    List<PieceDto> findPieceByGameId(final int gameId) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE game_id = ?";
        return jdbcTemplate.executeAndGet(query, rowMapper, String.valueOf(gameId));
    }

    void deleteAll(final RoomDto roomDto) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE game_id = ?";
        jdbcTemplate.execute(query, String.valueOf(roomDto.room_id()));
    }

    boolean hasRecords() {
        final String query = "SELECT * FROM " + TABLE_NAME + " LIMIT 1";
        final List<PieceDto> pieces = jdbcTemplate.executeAndGet(query, rowMapper);
        return !pieces.isEmpty();
    }
}
