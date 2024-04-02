package chess.dao;

import java.sql.Connection;

public interface DBConnectionCache {
    Connection getConnection();
}
