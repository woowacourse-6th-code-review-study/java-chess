package repository;

import db.JdbcTemplate;
import db.RowMapper;
import dto.TurnDto;

import java.util.List;

public class TurnDao {
    private static final String TABLE_NAME = "turns";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<TurnDto> rowMapper = (resultSet) ->
            new TurnDto(resultSet.getString("turn"));

    TurnDao() {
        this(new JdbcTemplate());
    }

    TurnDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    TurnDto findOne() {
        final String query = "SELECT * FROM " + TABLE_NAME + " LIMIT 1";
        final List<TurnDto> turns = jdbcTemplate.executeAndGet(query, rowMapper);
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
