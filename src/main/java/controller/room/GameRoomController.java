package controller.room;

import controller.room.command.RoomCommand;
import dto.RoomDto;
import dto.UserDto;
import service.GameRoomService;
import view.InputView;
import view.OutputView;

import java.util.List;

public class GameRoomController {
    private final GameRoomService roomService;

    public GameRoomController(GameRoomService roomService) {
        this.roomService = roomService;
    }

    public RoomDto loadRoom(UserDto user) {
        List<RoomDto> rooms = roomService.loadActiveRoomAll(user);
        OutputView.printGameRoomGuideMessage(rooms);

        RoomDto roomDto = readCommandUntilValid(user, rooms);
        OutputView.printEnteringRoomMessage(roomDto);
        return roomDto;
    }

    private RoomDto readCommandUntilValid(UserDto user, List<RoomDto> rooms) {
        try {
            RoomCommand command = InputView.readRoomCommand(rooms);
            return command.execute(roomService, user);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e);
            return readCommandUntilValid(user, rooms);
        }
    }
}
