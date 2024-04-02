package chess.dao;

import static chess.domain.piece.Team.BLACK;
import static chess.domain.piece.Team.WHITE;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.dao.exception.DBException;
import chess.domain.piece.Team;
import java.sql.Connection;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnDAOForMysqlTest {
    private static final String DB_URL = "jdbc:mysql://localhost:13306/chess2?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER_NAME = "user";
    private static final String DB_USER_PASSWORD = "password";
    private static final DBConnectionCache DB_CONNECTION_CACHE = new MysqlDBConnectionCache(DB_URL, DB_USER_NAME,
            DB_USER_PASSWORD);
    private static final TurnDAOForMysql TURN_DAO_FOR_MYSQL = new TurnDAOForMysql();

    @BeforeEach
    void setUp() {
        Connection connection = DB_CONNECTION_CACHE.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DBException("DB 접속 오류", e);
        }
    }

    @AfterEach
    void tearDown() {
        Connection connection = DB_CONNECTION_CACHE.getConnection();
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DBException("DB 접속 오류", e);
        }
    }

    @Test
    @DisplayName("현재 기물을 움직일 차례를 잘 불러오는지 검증")
    void select() {
        Connection connection = DB_CONNECTION_CACHE.getConnection();
        TURN_DAO_FOR_MYSQL.save(WHITE, connection);

        Team team = TURN_DAO_FOR_MYSQL.select(connection).get();
        Assertions.assertThat(team)
                .isEqualTo(WHITE);
    }

    @Test
    @DisplayName("현재 기물을 움직일 차례가 저징되어있는지 잘 판단하는지 검증")
    void isNotEmpty() {
        Connection connection = DB_CONNECTION_CACHE.getConnection();
        boolean notEmptyWhenTurnEmpty = TURN_DAO_FOR_MYSQL.isNotEmpty(connection);
        TURN_DAO_FOR_MYSQL.save(WHITE, connection);
        boolean notEmptyWhenTurnNotEmpty = TURN_DAO_FOR_MYSQL.isNotEmpty(connection);

        assertAll(
                () -> Assertions.assertThat(notEmptyWhenTurnEmpty).isFalse(),
                () -> Assertions.assertThat(notEmptyWhenTurnNotEmpty).isTrue()
        );
    }

    @Test
    @DisplayName("현재 기물을 움직일 차례를 잘 저장하는지 검증")
    void save() {
        Connection connection = DB_CONNECTION_CACHE.getConnection();
        boolean saveResult = TURN_DAO_FOR_MYSQL.save(WHITE, connection);

        Assertions.assertThat(saveResult)
                .isTrue();
    }

    @Test
    @DisplayName("현재 기물을 움직일 차례를 잘 수정하는지 검증")
    void update() {
        Connection connection = DB_CONNECTION_CACHE.getConnection();
        TURN_DAO_FOR_MYSQL.save(WHITE, connection);

        boolean updateResult = TURN_DAO_FOR_MYSQL.update(WHITE, BLACK, connection);

        Assertions.assertThat(updateResult)
                .isTrue();
    }

    @Test
    @DisplayName("현재 기물을 움직일 차례를 잘 지우는지 검증")
    void delete() {
        Connection connection = DB_CONNECTION_CACHE.getConnection();
        TURN_DAO_FOR_MYSQL.delete(connection);

        boolean notEmpty = TURN_DAO_FOR_MYSQL.isNotEmpty(connection);
        Assertions.assertThat(notEmpty)
                .isFalse();
    }
}
