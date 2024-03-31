package controller.room;

import dto.RoomDto;
import service.GameRoomService;
import view.InputView;
import view.OutputView;

import java.util.List;

public class GameRoomController {
    private final GameRoomService roomService;

    public GameRoomController(GameRoomService roomService) {
        this.roomService = roomService;
    }

    public RoomDto run() {
        List<RoomDto> rooms = roomService.loadActiveRoomAll();
        OutputView.printGameRoomGuideMessage(rooms);

        RoomDto roomDto = readCommandUntilValid(rooms);
        OutputView.printEnteringRoomMessage(roomDto);
        return roomDto;
    }

    private RoomDto readCommandUntilValid(List<RoomDto> rooms) {
        try {
            return InputView.readRoomCommand(rooms).execute(roomService);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e);
            return readCommandUntilValid(rooms);
        }
    }
}
