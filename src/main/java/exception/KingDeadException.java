package exception;

import constant.ErrorCode;

public class KingDeadException extends CustomException {

    public KingDeadException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
