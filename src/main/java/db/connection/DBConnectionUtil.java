package db.connection;

import static db.connection.ConnectionConst.OPTION;
import static db.connection.ConnectionConst.PASSWORD;
import static db.connection.ConnectionConst.SERVER;
import static db.connection.ConnectionConst.USERNAME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

    private DBConnectionUtil() {
    }

    public static Connection getConnection(final String database) {
        // 드라이버 연결
        try {
            final String url = "jdbc:mysql://" + SERVER.getValue() + "/" + database + OPTION.getValue();
            return DriverManager.getConnection(url, USERNAME.getValue(), PASSWORD.getValue());
        } catch (final SQLException exception) {
            System.err.println("DB 연결 오류:" + exception.getMessage());
            exception.printStackTrace();
            return null; // TODO null 을 리턴하는게 맞을지 예외 던지는게 맞을지
        }
    }
}
