package repository;

import dto.RoomDto;

import java.util.List;
import java.util.Optional;

public interface RoomDao {
    void add(RoomDto roomDto);

    Optional<RoomDto> addNewRoom();

    Optional<RoomDto> find(String roomId);

    List<RoomDto> findActiveRoomAll();
}
