package db.exception;

import constant.ErrorCode;

public class DBException extends RuntimeException {

    private final ErrorCode errorCode;

    public DBException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
