package db;

import static org.assertj.core.api.Assertions.assertThat;

import db.dto.TurnDto;
import model.Camp;
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
        turnDao.saveTurn(Camp.WHITE);
        final TurnDto expected = new TurnDto("WHITE");

        //when
        final TurnDto turn = turnDao.findTurn();

        //then
        assertThat(turn).isEqualTo(expected);
    }
}
