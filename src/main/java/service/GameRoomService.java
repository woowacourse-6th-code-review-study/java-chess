package service;

import db.JdbcTemplate;
import dto.RoomDto;
import repository.RoomDao;
import repository.RoomDaoImpl;

import java.util.List;
import java.util.Optional;

public class GameRoomService {
    private final RoomDao roomDao;

    GameRoomService(final RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public GameRoomService() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate();
        this.roomDao = new RoomDaoImpl(jdbcTemplate);
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
        return room.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));
    }
}
