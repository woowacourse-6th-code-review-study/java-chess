package view.command;

import controller.game.command.GameCommand;
import controller.game.command.EndOnGameCommand;
import controller.game.command.MoveOnGameCommand;
import controller.game.command.StartOnGameCommand;
import controller.game.command.StatusOnGameCommand;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum GameCommandType {
    START("start", StartOnGameCommand::new),
    END("end", EndOnGameCommand::new),
    STATUS("status", StatusOnGameCommand::new),
    MOVE("move", MoveOnGameCommand::new);

    private final String command;
    private final Function<List<String>, GameCommand> mapper;

    GameCommandType(final String command, final Function<List<String>, GameCommand> mapper) {
        this.command = command;
        this.mapper = mapper;
    }

    public static GameCommand getCommand(final CommandInput input) {
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
