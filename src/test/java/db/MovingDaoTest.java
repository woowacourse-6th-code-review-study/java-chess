package db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import db.connection.DBConnectionUtil;
import db.dto.MovingDto;
import db.exception.DaoException;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovingDaoTest {

    private final MovingDao movingDao = new MovingDao("chess_test");

    @BeforeEach
    void beforeEach() {
        movingDao.remove();
    }

    @DisplayName("데이터베이스 접속 확인")
    @Test
    void connection() throws SQLException {
        try (final Connection connection = DBConnectionUtil.getConnection("chess_test")) {
            assertThat(connection).isNotNull();
        }
    }

    @Test
    @DisplayName("이동 저장 확인")
    void addMoving() {
        final MovingDto moving = new MovingDto("WHITE", "a2", "a3");
        final long id = movingDao.addMoving(moving);

        assertThat(movingDao.findByMovementId(id)).isEqualTo(moving);
    }

    @Test
    @DisplayName("찾고자 하는 기보가 없으면 예외가 발생한다.")
    void failFindMoving() {
        //given
        movingDao.addMoving(new MovingDto("WHITE", "a2", "a3"));
        movingDao.addMoving(new MovingDto("BLACK", "h7", "h6"));

        //when then
        assertThatThrownBy(() -> movingDao.findByMovementId(3))
                .isInstanceOf(DaoException.class);
    }

    @Test
    @DisplayName("행의 개수를 파악한다.")
    void count() {
        assertThat(movingDao.countMoving()).isZero();
    }
}
