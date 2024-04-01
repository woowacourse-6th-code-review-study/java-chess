package controller.user;

import dto.UserDto;
import view.InputView;
import view.OutputView;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserDto run() {
        OutputView.printUserNameInputMessage();
        UserDto user = readCommandUntilValid();
        OutputView.printUserNameMessage(user);
        return user;
    }

    private UserDto readCommandUntilValid() {
        try {
            return InputView.readUserCommand().execute(userService);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e);
            return readCommandUntilValid();
        }
    }
}
