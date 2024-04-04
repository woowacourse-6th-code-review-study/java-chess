package view.command;

import controller.room.command.NewRoomOnRoomCommand;
import controller.room.command.RoomCommand;
import controller.room.command.SelectRoomOnRoomCommand;
import dto.RoomDto;

import java.util.Arrays;
import java.util.List;

public enum RoomCommandType {
    NEW_ROOM("new", (arguments, rooms) -> new NewRoomOnRoomCommand(arguments)),
    ROOM_SELECTION("room", SelectRoomOnRoomCommand::new);

    private final String command;
    private final CommandMapper mapper;

    RoomCommandType(final String command, final CommandMapper mapper) {
        this.command = command;
        this.mapper = mapper;
    }

    public static RoomCommand getCommand(final CommandInput input, final List<RoomDto> rooms) {
        final RoomCommandType commandType = Arrays.stream(RoomCommandType.values())
                .filter(command -> input.prefix().equals(command.command))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        return commandType.mapper.apply(input.getArguments(), rooms);
    }

    public String message() {
        return this.command;
    }

    @FunctionalInterface
    private interface CommandMapper {
        RoomCommand apply(List<String> arguments, List<RoomDto> rooms);
    }
}
