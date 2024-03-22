package model.status;

import constant.ErrorCode;
import exception.InvalidStatusException;
import java.util.List;
import model.GameBoard;

public class End implements GameStatus {

    @Override
    public GameStatus play(final List<String> command, final GameBoard gameBoard) {
        throw new InvalidStatusException(ErrorCode.INVALID_STATUS);
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
