package controller.user.command;

import dto.UserDto;

public interface Command {
    UserDto execute(UserService userService);
}
