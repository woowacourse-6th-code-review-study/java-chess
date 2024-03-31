package repository;

import dto.RoomDto;
import dto.TurnDto;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class RoomDaoTest {
    private final RoomDao roomDao = new RoomDao();
    private final TurnDao turnDao = new TurnDao();

    @Test
    void 새로운_방_번호를_생성한다() {
        assertThat(roomDao.addNewRoom().isPresent()).isTrue();
    }

    @Test
    void 활성화된_방을_모두_찾는다() {
        roomDao.add(new RoomDto(1));
        roomDao.add(new RoomDto(2));
        roomDao.add(new RoomDto(3));
        turnDao.add(new TurnDto("WHITE", "1"));
        turnDao.add(new TurnDto("GAMEOVER", "2"));
        turnDao.add(new TurnDto("BLACK", "3"));

        List<RoomDto> activeRoomAll = roomDao.findActiveRoomAll();

        assertThat(activeRoomAll).containsExactlyInAnyOrder(new RoomDto(1), new RoomDto(3));
    }
}
