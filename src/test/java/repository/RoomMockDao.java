package repository;

import dto.RoomDto;
import dto.StateDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RoomMockDao implements RoomDao {
    private final Map<Integer, RoomDto> roomRepository;
    private final Map<Integer, StateDto> turnRepository;

    public RoomMockDao(Map<Integer, RoomDto> roomRepository, Map<Integer, StateDto> turnRepository) {
        this.roomRepository = roomRepository;
        this.turnRepository = turnRepository;
    }

    public void add(final RoomDto roomDto) {
        roomRepository.put(roomDto.room_id(), roomDto);
    }

    public Optional<RoomDto> addNewRoom() {
        if (roomRepository.keySet().isEmpty()) {
            RoomDto newRoomDto = new RoomDto(1);
            roomRepository.put(newRoomDto.room_id(), newRoomDto);
            return Optional.of(newRoomDto);
        }

        int newRoomId = Collections.max(roomRepository.keySet()) + 1;
        RoomDto newRoomDto = new RoomDto(newRoomId);
        roomRepository.put(newRoomDto.room_id(), newRoomDto);
        return Optional.of(newRoomDto);
    }

    public Optional<RoomDto> find(final String roomId) {
        return Optional.of(roomRepository.get(Integer.parseInt(roomId)));
    }

    public List<RoomDto> findActiveRoomAll() {
        return roomRepository.keySet().stream()
                .filter(roomId -> !Objects.equals(turnRepository.get(roomId).state(), "GAMEOVER"))
                .map(roomRepository::get)
                .toList();
    }
}
