package repository;

import dto.RoomDto;
import dto.TurnDto;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class GameRoomServiceTest {
    private final RoomDto room1 = new RoomDto(1);
    private final RoomDto room2 = new RoomDto(2);
    private final RoomDto room3 = new RoomDto(3);

    private final Map<Integer, RoomDto> roomRepository = Map.of(
            room1.room_id(), room1,
            room2.room_id(), room2,
            room3.room_id(), room3);

    private final Map<Integer, TurnDto> turnRepository = Map.of(
            room1.room_id(), new TurnDto("GAMEOVER", String.valueOf(room1.room_id())),
            room2.room_id(), new TurnDto("WHITE", String.valueOf(room2.room_id())),
            room3.room_id(), new TurnDto("BLACK", String.valueOf(room3.room_id())));

    @Test
    void 활성화된_모든_방을_불러온다() {
        RoomDao roomDao = new RoomMockDao(roomRepository, turnRepository);
        GameRoomService gameRoomService = new GameRoomService(roomDao);

        assertThat(gameRoomService.loadActiveRoomAll())
                .containsExactlyInAnyOrder(room2, room3);
    }

    @Test
    void ID로_방을_찾는다() {
        RoomDao roomDao = new RoomMockDao(roomRepository, turnRepository);
        GameRoomService gameRoomService = new GameRoomService(roomDao);

        RoomDto findRoom = gameRoomService.findRoomById("1");

        assertThat(findRoom).isEqualTo(room1);
    }

    @Test
    void 새로운_방을_만든다() {
        RoomDao roomDao = new RoomMockDao(new HashMap<>(), new HashMap<>());
        GameRoomService gameRoomService = new GameRoomService(roomDao);

        assertThatCode(gameRoomService::createNewRoom)
                .doesNotThrowAnyException();
    }

    static class RoomMockDao extends RoomDao {
        private final Map<Integer, RoomDto> roomRepository;
        private final Map<Integer, TurnDto> turnRepository;

        public RoomMockDao(Map<Integer, RoomDto> roomRepository, Map<Integer, TurnDto> turnRepository) {
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
                    .filter(roomId -> !Objects.equals(turnRepository.get(roomId).turn(), "GAMEOVER"))
                    .map(roomRepository::get)
                    .toList();
        }
    }
}
