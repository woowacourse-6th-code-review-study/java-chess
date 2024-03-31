package repository;

import db.JdbcTemplate;
import db.RowMapper;
import dto.StateDto;

import java.util.List;
import java.util.Optional;

public class GameStateDaoImpl implements GameStateDao {
    private static final String TABLE_NAME = "game_states";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<StateDto> rowMapper = (resultSet) -> new StateDto(
            resultSet.getString("state"),
            resultSet.getInt("game_id")
    );

    GameStateDaoImpl() {
        this(new JdbcTemplate());
    }

    public GameStateDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(final StateDto stateDto) {
        final String query = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?)";
        jdbcTemplate.execute(query, String.valueOf(stateDto.gameId()), stateDto.state());
    }

    public Optional<StateDto> findByGameId(final int gameId) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE game_id = ? LIMIT 1";
        final List<StateDto> turns = jdbcTemplate.executeAndGet(query, rowMapper, String.valueOf(gameId));
        return Optional.ofNullable(turns.get(0));
    }

    public void deleteByGameId(final int gameId) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE game_id = ?";
        jdbcTemplate.execute(query, String.valueOf(gameId));
    }
}
