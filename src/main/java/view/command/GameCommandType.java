package view.command;

import controller.game.command.Command;
import controller.game.command.EndOnCommand;
import controller.game.command.MoveOnCommand;
import controller.game.command.StartOnCommand;
import controller.game.command.StatusOnCommand;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum GameCommandType {
    START("start", StartOnCommand::new),
    END("end", EndOnCommand::new),
    STATUS("status", StatusOnCommand::new),
    MOVE("move", MoveOnCommand::new);

    private final String command;
    private final Function<List<String>, Command> mapper;

    GameCommandType(final String command, final Function<List<String>, Command> mapper) {
        this.command = command;
        this.mapper = mapper;
    }

    public static Command getCommand(final CommandInput input) {
        final GameCommandType commandType = Arrays.stream(GameCommandType.values())
                .filter(command -> input.prefix().equals(command.command))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        return commandType.mapper.apply(input.getArguments());
    }

    public String message() {
        return this.command;
    }
}
