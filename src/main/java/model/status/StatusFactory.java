package model.status;

import constant.ErrorCode;
import exception.InvalidStatusException;
import model.command.CommandLine;

public class StatusFactory {

    private StatusFactory() {
    }

    public static GameStatus create(final CommandLine commandLine) {
        if (commandLine.isStart()) {
            return new Running();
        }
        if (commandLine.isEnd()) {
            return new End();
        }
        if (commandLine.isQuit()) {
            return new Quit();
        }
        throw new InvalidStatusException(ErrorCode.INVALID_STATUS);
    }
}
