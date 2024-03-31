package db.exception;

import constant.ErrorCode;

public class ConnectionException extends DBException {

    public ConnectionException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
