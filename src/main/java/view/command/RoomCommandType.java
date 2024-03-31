package view.command;

import controller.room.command.Command;
import controller.room.command.NewRoomOnCommand;
import controller.room.command.SelectRoomOnCommand;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum RoomCommandType {
    NEW_ROOM("new", NewRoomOnCommand::new),
    ROOM_SELECTION("room", SelectRoomOnCommand::new);

    private final String command;
    private final Function<List<String>, Command> mapper;

    RoomCommandType(final String command, final Function<List<String>, Command> mapper) {
        this.command = command;
        this.mapper = mapper;
    }

    public static Command getCommand(final CommandInput input) {
        final RoomCommandType commandType = Arrays.stream(RoomCommandType.values())
                .filter(command -> input.prefix().equals(command.command))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        return commandType.mapper.apply(input.getArguments());
    }

    public String message() {
        return this.command;
    }
}
