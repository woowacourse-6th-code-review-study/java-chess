package controller.room.command;

import dto.RoomDto;
import dto.UserDto;
import service.GameRoomService;

import java.util.List;

public class SelectRoomOnRoomCommand implements RoomCommand {
    private static final int ARGUMENT_SIZE = 1;

    private final String roomId;

    public SelectRoomOnRoomCommand(final List<String> arguments, final List<RoomDto> validRooms) {
        validateArgumentSize(arguments);
        validateRoomIdFormat(arguments.get(0));
        validateRoomIdRunning(validRooms, arguments.get(0));
        this.roomId = arguments.get(0);
    }

    private void validateArgumentSize(final List<String> arguments) {
        if (arguments.size() != ARGUMENT_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    private void validateRoomIdFormat(final String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    private void validateRoomIdRunning(final List<RoomDto> validRooms, final String input) {
        boolean isRunningRoomNotFound = validRooms.stream()
                .noneMatch(room -> room.room_id() == Integer.parseInt(input));
        if (isRunningRoomNotFound) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public RoomDto execute(final GameRoomService gameRoomService, final UserDto user) {
        return gameRoomService.findRoomById(roomId);
    }
}
