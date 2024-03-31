package chess.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface QueryProcessor {

    void process(PreparedStatement preparedStatement) throws SQLException;
}
