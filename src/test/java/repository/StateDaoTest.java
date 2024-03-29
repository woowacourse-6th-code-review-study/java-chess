package repository;

import domain.board.State;
import dto.StateDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StateDaoTest {
    @Test
    void 턴을_저장한다() {
        StateDto whiteTurn = StateDto.of(State.WHITE_TURN);
        StateDao stateDao = new StateDao();

        stateDao.update(whiteTurn);

        assertThat(stateDao.find().getState()).isEqualTo(State.WHITE_TURN);
    }
}
