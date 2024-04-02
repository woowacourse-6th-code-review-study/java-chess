package service;

import database.dao.RoomDao;
import dto.RoomDto;
import dto.UserDto;

import java.util.List;
import java.util.Optional;

public class GameRoomService {
    private final RoomDao roomDao;

    public GameRoomService(final RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public List<RoomDto> loadActiveRoomAll(final UserDto user) {
        return roomDao.findActiveRoomAll(user);
    }

    public RoomDto createNewRoom(final UserDto user) {
        final Optional<RoomDto> room = roomDao.addNewRoom(user);
        return room.orElseThrow(IllegalStateException::new);
    }

    public RoomDto findRoomById(final String roomId) {
        final Optional<RoomDto> room = roomDao.find(roomId);
        return room.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));
    }
}
