package controller.user.command;

import dto.UserDto;
import service.UserService;

public interface UserCommand {
    UserDto execute(UserService userService);
}
