package view.command;

import controller.room.command.Command;
import controller.room.command.NewRoomOnCommand;
import controller.room.command.SelectRoomOnCommand;
import dto.RoomDto;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public enum RoomCommandType {
    NEW_ROOM("new", NewRoomOnCommand::new),
    ROOM_SELECTION("room", SelectRoomOnCommand::new);

    private final String command;
    private final BiFunction<List<String>, List<RoomDto>, Command> mapper;

    RoomCommandType(final String command, final BiFunction<List<String>, List<RoomDto>, Command> mapper) {
        this.command = command;
        this.mapper = mapper;
    }

    public static Command getCommand(final CommandInput input, final List<RoomDto> rooms) {
        final RoomCommandType commandType = Arrays.stream(RoomCommandType.values())
                .filter(command -> input.prefix().equals(command.command))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        return commandType.mapper.apply(input.getArguments(), rooms);
    }

    public String message() {
        return this.command;
    }
}
