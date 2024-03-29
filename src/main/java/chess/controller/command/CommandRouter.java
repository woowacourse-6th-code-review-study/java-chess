package chess.controller.command;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum CommandRouter {
    START("start", Start::new),
    END("end", End::new),
    MOVE("move", Move::new),
    STATUS("status", Status::new);

    private static final int COMMAND_INDEX = 0;

    private final String value;
    private final Function<List<String>, Command> command;

    CommandRouter(String value,
                  Function<List<String>, Command> command) {
        this.value = value;
        this.command = command;
    }

    public static Command findCommendByInput(List<String> commandInput) {
        if (commandInput == null || commandInput.size() == 0) {
            throw new IllegalArgumentException("빈 값 입력을 허용하지 않습니다.");
        }
        return Arrays.stream(values())
                .filter(commandRouter -> commandRouter.value.equals(commandInput.get(COMMAND_INDEX)))
                .map(commandRouter -> commandRouter.command.apply(commandInput))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("옳바르지 않은 명령어 입력입니다."));
    }
}
