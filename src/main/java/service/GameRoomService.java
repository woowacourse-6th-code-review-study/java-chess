package service;

import dto.RoomDto;
import database.dao.RoomDao;
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
        Optional<RoomDto> room = roomDao.addNewRoom(user);
        return room.orElseThrow(IllegalStateException::new);
    }

    public RoomDto findRoomById(final String roomId) {
        Optional<RoomDto> room = roomDao.find(roomId);
        return room.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));
    }
}
