package database.dao;

import database.JdbcTemplate;
import database.RowMapper;
import dto.UserDto;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final String TABLE_NAME = "users";

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final RowMapper<UserDto> rowMapper = resultSet -> new UserDto(
            resultSet.getString("username")
    );

    @Override
    public Optional<UserDto> find(final String username) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? LIMIT 1";
        List<UserDto> users = jdbcTemplate.executeAndGet(query, rowMapper, username);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public void add(final String username) {
        final String query = "INSERT INTO " + TABLE_NAME + " VALUES (?)";
        jdbcTemplate.execute(query, username);
    }
}
