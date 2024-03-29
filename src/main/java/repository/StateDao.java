package repository;

import db.JdbcTemplate;
import db.RowMapper;
import dto.StateDto;

import java.util.List;

public class StateDao {
    private static final String TABLE_NAME = "states";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<StateDto> rowMapper = (resultSet) ->
            new StateDto(resultSet.getString("state"));

    StateDao() {
        this(new JdbcTemplate());
    }

    StateDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    StateDto find() {
        final var query = "SELECT * FROM " + TABLE_NAME + " LIMIT 1";
        final List<StateDto> turns = jdbcTemplate.find(query, rowMapper);
        if (turns.isEmpty()) {
            throw new IllegalArgumentException("데이터가 없습니다.");
        }
        return turns.get(0);
    }


    void update(final StateDto stateDto) {
        final String deleteQuery = "DELETE FROM " + TABLE_NAME;
        final String insertQuery = "INSERT INTO " + TABLE_NAME + " VALUES (?)";
        jdbcTemplate.delete(deleteQuery);
        jdbcTemplate.add(insertQuery, stateDto.state());
    }

    void deleteAll() {
        final String query = "DELETE FROM " + TABLE_NAME;
        jdbcTemplate.delete(query);
    }
}
