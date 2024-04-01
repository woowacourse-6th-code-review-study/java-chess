package service;

import database.dao.RoomDao;
import dto.RoomDto;
import dto.StateDto;
import dto.UserDto;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import repository.RoomMockDao;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class GameRoomServiceTest {
    private final RoomDto room1 = new RoomDto(1);
    private final RoomDto room2 = new RoomDto(2);
    private final RoomDto room3 = new RoomDto(3);
    private final UserDto MANGCHO = new UserDto("mangcho");

    private final Map<RoomDto, UserDto> roomRepository = Map.of(
            room1, MANGCHO,
            room2, MANGCHO,
            room3, MANGCHO);

    private final Map<Integer, StateDto> turnRepository = Map.of(
            room1.room_id(), new StateDto("GAMEOVER", room1.room_id()),
            room2.room_id(), new StateDto("WHITE", room2.room_id()),
            room3.room_id(), new StateDto("BLACK", room3.room_id()));

    @Test
    void 활성화된_모든_방을_불러온다() {
        RoomDao roomDao = new RoomMockDao(roomRepository, turnRepository);
        GameRoomService gameRoomService = new GameRoomService(roomDao);

        assertThat(gameRoomService.loadActiveRoomAll(MANGCHO))
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
    void 세번째_방까지_생성된_시점에_새로_생성한_방은_4번_방이다() {
        RoomDao roomDao = new RoomMockDao(new HashMap<>(roomRepository), new HashMap<>());
        GameRoomService gameRoomService = new GameRoomService(roomDao);

        RoomDto newRoom = gameRoomService.createNewRoom(MANGCHO);

        assertThat(newRoom).isEqualTo(new RoomDto(4));
    }
}
