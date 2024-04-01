package chess.dao;

import static chess.domain.Position.A2;
import static chess.domain.Position.B2;
import static chess.domain.piece.Team.WHITE;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.dao.exception.DBException;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecesOnChessBoardDAOForMysqlTest {
    private static final String DB_URL = "jdbc:mysql://localhost:13306/chess2?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER_NAME = "user";
    private static final String DB_USER_PASSWORD = "password";
    private static final DBConnectionCache DB_CONNECTION_CACHE = new MysqlDBConnectionCache(DB_URL, DB_USER_NAME,
            DB_USER_PASSWORD);
    private static final PiecesOnChessBoardDAOForMysql DAO_FOR_MYSQL = new PiecesOnChessBoardDAOForMysql();

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
    @DisplayName("체스 보드 위의 기물 정보가 잘 저장되는지 검증")
    void save() {
        Connection connection = DB_CONNECTION_CACHE.getConnection();
        boolean saveResult = DAO_FOR_MYSQL.save(new Pawn(A2, WHITE), connection);
        Assertions.assertThat(saveResult)
                .isTrue();
    }

    @Test
    @DisplayName("체스 보드 위의 여러 기물 정보가 잘 저장되는지 검증")
    void saveAll() {
        Connection connection = DB_CONNECTION_CACHE.getConnection();
        boolean saveResult = DAO_FOR_MYSQL.saveAll(List.of(new Pawn(A2, WHITE), new Pawn(B2, WHITE)), connection);
        Assertions.assertThat(saveResult)
                .isTrue();
    }

    @Test
    @DisplayName("저장된 체스 보드 위의 여러 기물 정보를 잘 불러오는지 검증")
    void selectAll() {
        Connection connection = DB_CONNECTION_CACHE.getConnection();
        List<Piece> pieces = List.of(new Pawn(A2, WHITE), new Pawn(B2, WHITE));
        DAO_FOR_MYSQL.saveAll(pieces, connection);

        List<Piece> selected = DAO_FOR_MYSQL.selectAll(connection);

        Assertions.assertThat(selected)
                .containsExactlyInAnyOrderElementsOf(pieces);
    }

    @Test
    @DisplayName("체스 보드 위에 말이 있는지 잘 판단하는지 검증")
    void isNotEmpty() {
        Connection connection = DB_CONNECTION_CACHE.getConnection();
        boolean notEmptyWhenBoardIsEmpty = DAO_FOR_MYSQL.isNotEmpty(connection);
        DAO_FOR_MYSQL.save(new Pawn(A2, WHITE), connection);
        boolean notEmptyWhenBoardIsNotEmpty = DAO_FOR_MYSQL.isNotEmpty(connection);
        assertAll(
                () -> Assertions.assertThat(notEmptyWhenBoardIsEmpty).isFalse(),
                () -> Assertions.assertThat(notEmptyWhenBoardIsNotEmpty).isTrue()
        );
    }

    @Test
    @DisplayName("체스 보드 위의 말을 잘 지우는지 검증")
    void delete() {
        Connection connection = DB_CONNECTION_CACHE.getConnection();
        DAO_FOR_MYSQL.save(new Pawn(A2, WHITE), connection);

        boolean deleteResult = DAO_FOR_MYSQL.delete(A2, connection);

        Assertions.assertThat(deleteResult)
                .isTrue();
    }

    @Test
    @DisplayName("체스 보드 위의 모든 말을 잘 지우는지 검증")
    void deleteAll() {
        Connection connection = DB_CONNECTION_CACHE.getConnection();
        List<Piece> pieces = List.of(new Pawn(A2, WHITE), new Pawn(B2, WHITE));
        DAO_FOR_MYSQL.saveAll(pieces, connection);

        DAO_FOR_MYSQL.deleteAll(connection);

        List<Piece> selected = DAO_FOR_MYSQL.selectAll(connection);
        Assertions.assertThat(selected)
                .isEmpty();
    }
}
