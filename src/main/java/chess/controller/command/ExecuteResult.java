package chess.controller.command;

public class ExecuteResult {
    private final boolean success;
    private final boolean needNextCommand;

    public ExecuteResult(boolean success, boolean needNextCommand) {
        this.success = success;
        this.needNextCommand = needNextCommand;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isNeedNextCommand() {
        return needNextCommand;
    }
}
