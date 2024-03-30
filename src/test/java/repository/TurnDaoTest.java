package repository;

import domain.piece.Color;
import dto.TurnDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TurnDaoTest {
    @Test
    void 턴을_저장한다() {
        TurnDto whiteTurn = TurnDto.of(Color.WHITE);
        TurnDao turnDao = new TurnDao();

        turnDao.update(whiteTurn);

        assertThat(turnDao.find().getTurn()).isEqualTo(Color.WHITE);
    }
}
