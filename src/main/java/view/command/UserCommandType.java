package view.command;

import controller.user.command.FindUserOnDemand;
import controller.user.command.UserCommand;

import java.util.Arrays;
import java.util.List;

public enum UserCommandType {
    FIND_USER("user", FindUserOnDemand::new);

    private final String command;
    private final CommandMapper mapper;

    UserCommandType(final String command, final CommandMapper mapper) {
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

    @FunctionalInterface
    private interface CommandMapper {
        UserCommand apply(List<String> arguments);
    }
}
