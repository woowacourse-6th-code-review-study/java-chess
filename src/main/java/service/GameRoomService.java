package service;

import database.dao.RoomDao;
import dto.RoomDto;
import dto.UserDto;

import java.util.List;

public class GameRoomService {
    private final RoomDao roomDao;

    public GameRoomService(final RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public List<RoomDto> loadActiveRoomAll(final UserDto user) {
        return roomDao.findActiveRoomAll(user);
    }

    public RoomDto createNewRoom(final UserDto user) {
        return roomDao.addNewRoom(user)
                .orElseThrow(IllegalStateException::new);
    }

    public RoomDto findRoomById(final String roomId) {
        return roomDao.find(roomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));
    }
}
