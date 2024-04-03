package controller.user;

import controller.user.command.UserCommand;
import dto.UserDto;
import service.UserService;
import view.InputView;
import view.OutputView;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserDto loadUser() {
        OutputView.printUserNameInputMessage();
        UserDto user = readCommandUntilValid();
        OutputView.printUserNameMessage(user);
        return user;
    }

    private UserDto readCommandUntilValid() {
        try {
            UserCommand command = InputView.readUserCommand();
            return command.execute(userService);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e);
            return readCommandUntilValid();
        }
    }
}
