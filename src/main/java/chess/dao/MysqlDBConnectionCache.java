package chess.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDBConnectionCache implements DBConnectionCache {
    private static final String DB_URL = "jdbc:mysql://localhost:13306/chess?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER_NAME = "user";
    private static final String DB_USER_PASSWORD = "password";
    private final DBConnectionParameters dbConnectionParameters;
    private Connection connection;

    public MysqlDBConnectionCache() {
        this(DB_URL, DB_USER_NAME, DB_USER_PASSWORD);
    }

    public MysqlDBConnectionCache(String url, String userName, String password) {
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

    @Override
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
