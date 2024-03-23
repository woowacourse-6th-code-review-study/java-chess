package model.status;

import constant.ErrorCode;
import exception.InvalidStatusException;
import java.util.List;
import model.Command;

public class Initialization {

    private Initialization() {
    }

    public static GameStatus gameSetting(List<String> command) {
        Command cmd = Command.from(command.get(0));
        if (cmd == Command.START && command.size() == 1) {
            return new Running();
        }
        if (cmd == Command.END && command.size() == 1) {
            return new End();
        }
        throw new InvalidStatusException(ErrorCode.INVALID_STATUS);
    }
}