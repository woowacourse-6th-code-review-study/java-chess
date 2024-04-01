package controller.room.command;

import dto.RoomDto;
import dto.UserDto;
import service.GameRoomService;

import java.util.List;

public class NewRoomOnCommand implements Command {
    private static final int ARGUMENT_SIZE = 0;

    public NewRoomOnCommand(final List<String> arguments) {
        validateArgumentSize(arguments);
    }

    private void validateArgumentSize(final List<String> arguments) {
        if (arguments.size() != ARGUMENT_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public RoomDto execute(final GameRoomService gameRoomService, final UserDto user) {
        return gameRoomService.createNewRoom(user);
    }
}
