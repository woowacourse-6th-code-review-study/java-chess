package repository;

import domain.piece.Color;
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
        TurnDto whiteTurn = TurnDto.of(Color.WHITE);
        TurnDao turnDao = new TurnDao();

        turnDao.update(whiteTurn);

        assertThat(turnDao.find().getTurn()).isEqualTo(Color.WHITE);
    }
}
