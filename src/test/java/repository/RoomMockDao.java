package repository;

import database.dao.RoomDao;
import dto.RoomDto;
import dto.StateDto;
import dto.UserDto;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RoomMockDao implements RoomDao {
    private final Map<RoomDto, UserDto> roomRepository;
    private final Map<Integer, StateDto> turnRepository;

    public RoomMockDao(Map<RoomDto, UserDto> roomRepository, Map<Integer, StateDto> turnRepository) {
        this.roomRepository = roomRepository;
        this.turnRepository = turnRepository;
    }

    public void add(UserDto userDto, RoomDto roomDto) {
        roomRepository.put(roomDto, userDto);
    }

    public Optional<RoomDto> addNewRoom(UserDto userDto) {
        int newRoomId = roomRepository.keySet()
                .stream()
                .mapToInt(RoomDto::room_id)
                .max()
                .orElse(0) + 1;
        RoomDto newRoom = new RoomDto(newRoomId);
        roomRepository.put(newRoom, userDto);
        return Optional.of(newRoom);
    }

    public Optional<RoomDto> find(final String roomId) {
        return roomRepository.keySet().stream()
                .filter(room -> room.room_id() == Integer.parseInt(roomId))
                .findFirst();
    }

    public List<RoomDto> findActiveRoomAll(final UserDto user) {
        return roomRepository.entrySet().stream()
                .filter(entry -> entry.getValue().equals(user))
                .filter(entry -> !Objects.equals(turnRepository.get(entry.getKey().room_id()).state(), "GAMEOVER"))
                .map(Map.Entry::getKey)
                .toList();
    }
}
