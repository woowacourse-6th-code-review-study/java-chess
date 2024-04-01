package database.dao;

import dto.RoomDto;
import dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface RoomDao {
    void add(UserDto userDto, RoomDto roomDto);

    Optional<RoomDto> addNewRoom(UserDto userDto);

    Optional<RoomDto> find(String roomId);

    List<RoomDto> findActiveRoomAll(final UserDto user);
}
