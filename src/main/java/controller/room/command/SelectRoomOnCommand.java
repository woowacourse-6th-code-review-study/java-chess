package controller.room.command;

import dto.RoomDto;
import service.GameRoomService;

import java.util.List;

public class SelectRoomOnCommand implements Command {
    private static final int ARGUMENT_SIZE = 1;

    private final String roomId;

    public SelectRoomOnCommand(final List<String> arguments) {
        validateArgumentSize(arguments);
        validateRoomId(arguments.get(0));
        this.roomId = arguments.get(0);
    }

    private void validateArgumentSize(final List<String> arguments) {
        if (arguments.size() != ARGUMENT_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    private void validateRoomId(final String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public RoomDto execute(final GameRoomService gameRoomService) {
        return gameRoomService.findRoomById(roomId);
    }
}
