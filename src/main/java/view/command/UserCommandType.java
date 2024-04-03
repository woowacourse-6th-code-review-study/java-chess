package view.command;

import controller.user.command.UserCommand;
import controller.user.command.FindUserOnDemand;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum UserCommandType {
    FIND_USER("user", FindUserOnDemand::new);

    private final String command;
    private final Function<List<String>, UserCommand> mapper;

    UserCommandType(final String command, final Function<List<String>, UserCommand> mapper) {
        this.command = command;
        this.mapper = mapper;
    }

    public static UserCommand getCommand(final CommandInput input) {
        final UserCommandType commandType = Arrays.stream(UserCommandType.values())
                .filter(command -> input.prefix().equals(command.command))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        return commandType.mapper.apply(input.getArguments());
    }

    public String message() {
        return this.command;
    }
}
