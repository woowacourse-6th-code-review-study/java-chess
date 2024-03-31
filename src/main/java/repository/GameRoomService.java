package repository;

import db.JdbcTemplate;
import dto.RoomDto;

import java.util.List;
import java.util.Optional;

public class GameRoomService {
    private final RoomDao roomDao;

    GameRoomService(final RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public GameRoomService() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate();
        this.roomDao = new RoomDao(jdbcTemplate);
    }

    public List<RoomDto> loadActiveRoomAll() {
        return roomDao.findActiveRoomAll();
    }

    public RoomDto createNewRoom() {
        Optional<RoomDto> room = roomDao.addNewRoom();
        return room.orElseThrow(IllegalStateException::new);
    }

    public RoomDto findRoomById(final String roomId) {
        Optional<RoomDto> room = roomDao.find(roomId);
        return room.orElseThrow(IllegalArgumentException::new);
    }
}
