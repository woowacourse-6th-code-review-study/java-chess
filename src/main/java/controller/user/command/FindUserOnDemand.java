package controller.user.command;

import dto.UserDto;
import service.UserService;

import java.util.List;

public class FindUserOnDemand implements Command {
    private static final int ARGUMENT_SIZE = 1;
    private static final int MINIMUM_NAME_LENGTH = 4;
    private static final int MAXIMUM_NAME_LENGTH = 10;

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
        if (input.length() < MINIMUM_NAME_LENGTH || input.length() > MAXIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public UserDto execute(UserService userService) {
        return userService.findByUsername(username);
    }
}
