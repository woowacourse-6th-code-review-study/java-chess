package database.dao;

import database.JdbcTemplate;
import database.RowMapper;
import dto.RoomDto;
import dto.UserDto;

import java.util.List;
import java.util.Optional;

public class RoomDaoImpl implements RoomDao {
    private static final String TABLE_NAME = "rooms";
    private static final String GAME_STATUES_TABLE_NAME = "game_states";

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final RowMapper<RoomDto> rowMapper = (resultSet) -> new RoomDto(
            resultSet.getInt("room_id")
    );

    public void add(final UserDto userDto, final RoomDto roomDto) {
        final String insertQuery = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?)";
        jdbcTemplate.execute(insertQuery, userDto.username(), "" + roomDto.room_id());
    }

    public Optional<RoomDto> addNewRoom(final UserDto userDto) {
        final int newRoomId = getRoomIdMax() + 1;
        return Optional.of(insertNewRoom(userDto, newRoomId));
    }

    private int getRoomIdMax() {
        final String selectQuery = "SELECT MAX(room_id) AS room_id FROM " + TABLE_NAME;
        final List<RoomDto> rooms = jdbcTemplate.executeAndGet(selectQuery, rowMapper);
        return rooms.get(0).room_id();
    }

    private RoomDto insertNewRoom(final UserDto userDto, final int newRoomId) {
        final String insertQuery = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?)";
        jdbcTemplate.execute(insertQuery, userDto.username(), "" + newRoomId);
        return new RoomDto(newRoomId);
    }

    public Optional<RoomDto> find(final String roomId) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE room_id = ?";
        final List<RoomDto> rooms = jdbcTemplate.executeAndGet(query, rowMapper, roomId);
        if (rooms.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(rooms.get(0));
    }

    public List<RoomDto> findActiveRoomAll(final UserDto user) {
        final String query = "SELECT * FROM " + TABLE_NAME + " AS r JOIN "
                + GAME_STATUES_TABLE_NAME + " AS s ON r.room_id = s.game_id WHERE r.user = ? and s.state != 'GAMEOVER'";
        return jdbcTemplate.executeAndGet(query, rowMapper, user.username());
    }
}
