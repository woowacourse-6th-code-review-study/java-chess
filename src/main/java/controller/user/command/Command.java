package controller.user.command;

import dto.UserDto;
import service.UserService;

public interface Command {
    UserDto execute(UserService userService);
}
