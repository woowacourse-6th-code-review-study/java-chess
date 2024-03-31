package repository;

import domain.piece.Color;
import dto.RoomDto;
import dto.TurnDto;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class TurnDaoTest {
    @Test
    void 턴을_저장한다() {
        TurnDto whiteTurn = new TurnDto("WHITE", 1);
        RoomDto roomDto = new RoomDto(whiteTurn.gameId());
        TurnDao turnDao = new TurnDao();

        turnDao.update(whiteTurn);

        assertThat(turnDao.findTurnByGameId(roomDto).getTurn()).isEqualTo(Color.WHITE);
    }
}
