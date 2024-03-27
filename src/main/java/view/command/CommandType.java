package view.command;

import controller.command.Command;
import controller.command.EndOnCommand;
import controller.command.MoveOnCommand;
import controller.command.StartOnCommand;
import controller.command.StatusOnCommand;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum CommandType {
    START("start", StartOnCommand::new),
    END("end", EndOnCommand::new),
    STATUS("status", StatusOnCommand::new),
    MOVE("move", MoveOnCommand::new);

    private final String command;
    private final Function<List<String>, Command> mapper;

    CommandType(final String command, final Function<List<String>, Command> mapper) {
        this.command = command;
        this.mapper = mapper;
    }

    public static Command getCommand(final CommandInput input) {
        final CommandType commandType = Arrays.stream(CommandType.values())
                .filter(command -> input.prefix().equals(command.command))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        return commandType.mapper.apply(input.getArguments());
    }

    public String message() {
        return this.command;
    }
}
