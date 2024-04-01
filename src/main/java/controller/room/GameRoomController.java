package controller.room;

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

    public RoomDto run(UserDto user) {
        List<RoomDto> rooms = roomService.loadActiveRoomAll(user);
        OutputView.printGameRoomGuideMessage(rooms);

        RoomDto roomDto = readCommandUntilValid(user, rooms);
        OutputView.printEnteringRoomMessage(roomDto);
        return roomDto;
    }

    private RoomDto readCommandUntilValid(UserDto user, List<RoomDto> rooms) {
        try {
            return InputView.readRoomCommand(rooms).execute(roomService, user);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e);
            return readCommandUntilValid(user, rooms);
        }
    }
}
