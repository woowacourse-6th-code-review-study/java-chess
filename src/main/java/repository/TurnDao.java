package repository;

import db.JdbcTemplate;
import db.RowMapper;
import dto.RoomDto;
import dto.TurnDto;

import java.util.List;

public class TurnDao {
    private static final String TABLE_NAME = "game_states";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<TurnDto> rowMapper = (resultSet) -> new TurnDto(
            resultSet.getString("state"),
            resultSet.getInt("game_id")
    );

    TurnDao() {
        this(new JdbcTemplate());
    }

    TurnDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void add(final TurnDto turnDto) {
        final String query = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?)";
        jdbcTemplate.execute(query, turnDto.turn(), String.valueOf(turnDto.gameId()));
    }

    TurnDto findTurnByGameId(final RoomDto roomDto) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE game_id = ?";
        final List<TurnDto> turns = jdbcTemplate.executeAndGet(query, rowMapper, String.valueOf(roomDto.room_id()));
        if (turns.isEmpty()) {
            throw new IllegalArgumentException("데이터가 없습니다.");
        }
        return turns.get(0);
    }

    void update(final TurnDto turnDto) {
        final String deleteQuery = "DELETE FROM " + TABLE_NAME;
        final String insertQuery = "INSERT INTO " + TABLE_NAME + " VALUES (?)";
        jdbcTemplate.execute(deleteQuery);
        jdbcTemplate.execute(insertQuery, turnDto.turn());
    }

    void deleteAll() {
        final String query = "DELETE FROM " + TABLE_NAME;
        jdbcTemplate.execute(query);
    }
}
