package db;

import static org.assertj.core.api.Assertions.assertThat;

import db.connection.DBConnectionUtil;
import db.dto.MovingDto;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovingDaoTest {

    private final MovingDao movingDao = new MovingDao("chess_test");

    @BeforeEach
    void beforeEach() {
        movingDao.remove("moving");
    }

    @DisplayName("데이터베이스 접속 확인")
    @Test
    void connection() throws SQLException {
        try (final var connection = DBConnectionUtil.getConnection("chess_test")) {
            assertThat(connection).isNotNull();
        }
    }

    @Test
    @DisplayName("이동 저장 확인")
    void addMoving() {
        final var moving = new MovingDto("WHITE", "a", "2", "a", "3");
        final var id = movingDao.addMoving(moving);

        assertThat(movingDao.findByMovementId(id)).isEqualTo(moving);
    }
}
