package chess.dao;

import static chess.domain.piece.Team.BLACK;
import static chess.domain.piece.Team.WHITE;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.piece.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnDAOForMysqlTest {
    private static final String DB_URL = "jdbc:mysql://localhost:13306/chess?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER_NAME = "user";
    private static final String DB_USER_PASSWORD = "password";

    @BeforeEach
    public void initDB() throws SQLException {
        DBConnectionCache dbConnectionCache = new DBConnectionCache(DB_URL, DB_USER_NAME, DB_USER_PASSWORD);
        Connection connection = dbConnectionCache.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete FROM game");
        preparedStatement.executeUpdate();
        connection.close();
    }

    @Test
    @DisplayName("턴을 잘 조회할 수 있는지 검증")
    void select() {
        TurnDAO turnDAO = new TurnDAOForMysql();
        turnDAO.save(WHITE);
        Team selected = turnDAO.select().orElseThrow();
        Assertions.assertThat(selected)
                .isEqualTo(WHITE);
    }

    @Test
    @DisplayName("턴을 잘 저장할 수 있는지 검증")
    void save() {
        TurnDAO turnDAO = new TurnDAOForMysql();
        boolean saveSuccess = turnDAO.save(WHITE);
        Assertions.assertThat(saveSuccess)
                .isTrue();
    }

    @Test
    @DisplayName("턴이 동시에 여러개가 되도록 집어넣을 수 없는지 검증")
    void saveFailCauseDuplicateTurn() {
        TurnDAO turnDAO = new TurnDAOForMysql();
        boolean saveSuccess = turnDAO.save(WHITE);
        boolean expectedToFalse = turnDAO.save(BLACK);
        assertAll(
                () -> Assertions.assertThat(saveSuccess)
                        .isTrue(),
                () -> Assertions.assertThat(expectedToFalse)
                        .isFalse()
        );
    }

    @Test
    @DisplayName("턴을 잘 변경할 수 있는지 검증")
    void update() {
        TurnDAO turnDAO = new TurnDAOForMysql();
        turnDAO.save(BLACK);
        boolean updateSuccess = turnDAO.update(BLACK, WHITE);
        Assertions.assertThat(updateSuccess)
                .isTrue();
    }
}
