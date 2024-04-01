package db;

import static org.assertj.core.api.Assertions.assertThat;

import db.dto.TurnDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnDaoTest {

    private final TurnDao turnDao = new TurnDao("chess_test");

    @BeforeEach
    void beforeEach() {
        turnDao.remove();
    }

    @DisplayName("턴 저장 확인")
    @Test
    void saveTurn() {
        //given
        final TurnDto turnDto = new TurnDto("WHITE", 1);
        turnDao.saveTurn(turnDto);
        final TurnDto expected = new TurnDto("WHITE", 1);

        //when
        final TurnDto turn = turnDao.findTurn();

        //then
        assertThat(turn).isEqualTo(expected);
    }
}
