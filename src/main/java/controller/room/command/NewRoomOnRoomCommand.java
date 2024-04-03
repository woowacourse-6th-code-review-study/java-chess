package controller.room.command;

import dto.RoomDto;
import dto.UserDto;
import service.GameRoomService;

import java.util.List;

public class NewRoomOnRoomCommand implements RoomCommand {
    public NewRoomOnRoomCommand(final List<String> arguments) {
        validateArgumentSize(arguments);
    }

    private void validateArgumentSize(final List<String> arguments) {
        if (!arguments.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public RoomDto execute(final GameRoomService gameRoomService, final UserDto user) {
        return gameRoomService.createNewRoom(user);
    }
}
