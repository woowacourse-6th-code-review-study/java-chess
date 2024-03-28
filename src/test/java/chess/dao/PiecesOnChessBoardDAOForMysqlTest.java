package chess.dao;

import static chess.domain.Position.A1;
import static chess.domain.Position.A2;
import static chess.domain.Position.A4;
import static chess.domain.piece.Team.BLACK;
import static chess.domain.piece.Team.WHITE;

import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecesOnChessBoardDAOForMysqlTest {
    private static final String DB_URL = "jdbc:mysql://localhost:13306/chess?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER_NAME = "user";
    private static final String DB_USER_PASSWORD = "password";

    @BeforeEach
    public void initDB() throws SQLException {
        DBConnectionCache dbConnectionCache = new DBConnectionCache(DB_URL, DB_USER_NAME, DB_USER_PASSWORD);
        Connection connection = dbConnectionCache.getConnection();
        deleteAllData(connection);
        insertInitialData(connection);
        connection.close();
    }

    private static void deleteAllData(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete FROM pieces_on_board");
        preparedStatement.execute();
    }

    private static void insertInitialData(Connection connection) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(
                "insert into pieces_on_board(piece_type, team_name, position_name) values ( ? ,? ,? ), (?, ?, ?)");
        preparedStatement.setString(1, "PAWN");
        preparedStatement.setString(2, "WHITE");
        preparedStatement.setString(3, "A1");
        preparedStatement.setString(4, "PAWN");
        preparedStatement.setString(5, "BLACK");
        preparedStatement.setString(6, "A2");
        preparedStatement.execute();
    }

    @Test
    @DisplayName("데이터베이스에 저장되어있는 모든 기물이 제대로 조회되는지 검증")
    void selectAll() {
        PiecesOnChessBoardDAO dao = new PiecesOnChessBoardDAOForMysql();
        List<Piece> selected = dao.selectAll();
        Assertions.assertThat(selected)
                .containsExactlyInAnyOrder(new Pawn(A1, WHITE), new Pawn(A2, BLACK));
    }

    @Test
    @DisplayName("데이터베이스에서 특정 기물을 지울 수 있는지 검증")
    void delete() {
        PiecesOnChessBoardDAO dao = new PiecesOnChessBoardDAOForMysql();

        boolean deleteSuccess = dao.delete(A2);
        Assertions.assertThat(deleteSuccess)
                .isTrue();
    }

    @Test
    @DisplayName("데이터베이스에서 특정 기물을 저장할 수 있는지 검증")
    void save() {
        PiecesOnChessBoardDAO dao = new PiecesOnChessBoardDAOForMysql();

        boolean saveSuccess = dao.save(new Pawn(A4, BLACK));
        Assertions.assertThat(saveSuccess)
                .isTrue();
    }

    @Test
    @DisplayName("특정 위치의 기물을 다른 것으로 변경할 수 있는지 검증")
    void update() {
        PiecesOnChessBoardDAO dao = new PiecesOnChessBoardDAOForMysql();
        boolean success = dao.update(A2, PieceType.BISHOP, BLACK);
        Assertions.assertThat(success)
                .isTrue();
    }
}
