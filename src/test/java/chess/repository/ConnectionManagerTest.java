package chess.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConnectionManagerTest {
    private final ConnectionManager connectionManager = new ConnectionManager();

    @DisplayName("DB 커넥션을 성공적으로 얻어올 수 있는지 테스트")
    @Test
    void should_ConnectionManagerCouldGetConnection() {
        try (final var connection = connectionManager.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
