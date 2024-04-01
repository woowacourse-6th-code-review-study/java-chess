package controller.user.command;

import dto.UserDto;

import java.util.List;

public class FindUserOnDemand implements Command {
    private static final int ARGUMENT_SIZE = 1;

    private final String username;

    public FindUserOnDemand(final List<String> arguments) {
        validateArgumentSize(arguments);
        validateUsernameFormat(arguments.get(0));
        this.username = arguments.get(0);
    }

    private void validateArgumentSize(final List<String> arguments) {
        if (arguments.size() != ARGUMENT_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    private void validateUsernameFormat(final String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public UserDto execute(UserService userService) {
        userService.findByUsername(username);
        return null;
    }
}
