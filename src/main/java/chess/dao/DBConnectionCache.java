package chess.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionCache {
    private final DBConnectionParameters dbConnectionParameters;
    private Connection connection;

    public DBConnectionCache(String url, String userName, String password) {
        dbConnectionParameters = new DBConnectionParameters(url, userName, password);
        connection = createConnection(dbConnectionParameters);
    }

    private Connection createConnection(DBConnectionParameters dbConnectionParameters) {
        String url = dbConnectionParameters.url;
        String userName = dbConnectionParameters.userName;
        String password = dbConnectionParameters.password;
        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException("DB 접속 에러", e);
        }
    }

    public Connection getConnection() {
        try {
            return getConnectionCanThrow();
        } catch (SQLException e) {
            throw new RuntimeException("DB 접속 에러");
        }
    }

    private Connection getConnectionCanThrow() throws SQLException {
        if (!connection.isValid(1)) {
            connection = createConnection(dbConnectionParameters);
        }
        return connection;
    }

    private record DBConnectionParameters(String url, String userName, String password) {
    }
}
