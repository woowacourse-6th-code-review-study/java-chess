package database.dao;

import database.JdbcTemplate;
import database.RowMapper;
import dto.PieceDto;
import dto.RoomDto;

import java.util.List;

public class PieceDaoImpl implements PieceDao {
    private static final String TABLE_NAME = "pieces";

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final RowMapper<PieceDto> rowMapper = (resultSet) -> new PieceDto(
            resultSet.getString("board_file"),
            resultSet.getString("board_rank"),
            resultSet.getString("color"),
            resultSet.getString("type")
    );

    public PieceDaoImpl() {
    }

    public void add(final RoomDto room, final PieceDto piece) {
        final String query = "INSERT INTO " + TABLE_NAME + " VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.execute(query,
                String.valueOf(room.room_id()), piece.boardFile(), piece.boardRank(),
                piece.color(), piece.type());
    }

    public List<PieceDto> findPieceByGameId(final int gameId) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE game_id = ?";
        return jdbcTemplate.executeAndGet(query, rowMapper, String.valueOf(gameId));
    }

    public void deleteAllByGameId(final int gameId) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE game_id = ?";
        jdbcTemplate.execute(query, String.valueOf(gameId));
    }
}
