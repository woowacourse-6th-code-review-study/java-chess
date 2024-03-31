package db.exception;

import constant.ErrorCode;

public class DaoException extends DBException {

    public DaoException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
