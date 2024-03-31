package db.connection;

import static db.connection.ConnectionConst.OPTION;
import static db.connection.ConnectionConst.PASSWORD;
import static db.connection.ConnectionConst.SERVER;
import static db.connection.ConnectionConst.USERNAME;

import constant.ErrorCode;
import db.exception.ConnectionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

    private DBConnectionUtil() {
    }

    public static Connection getConnection(final String database) {
        try {
            final String url = "jdbc:mysql://" + SERVER.getValue() + "/" + database + OPTION.getValue();
            return DriverManager.getConnection(url, USERNAME.getValue(), PASSWORD.getValue());
        } catch (final SQLException exception) {
            throw new ConnectionException(ErrorCode.CONNECTION);
        }
    }
}
