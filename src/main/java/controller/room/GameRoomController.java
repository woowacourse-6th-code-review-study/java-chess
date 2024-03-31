package controller.room;

import dto.RoomDto;
import service.GameRoomService;
import view.InputView;
import view.OutputView;

import java.util.List;

public class GameRoomController {
    private final GameRoomService roomService;

    public GameRoomController(final GameRoomService roomService) {
        this.roomService = roomService;
    }

    public RoomDto run() {
        List<RoomDto> rooms = roomService.loadActiveRoomAll();
        OutputView.printGameRoomGuideMessage(rooms);

        RoomDto roomDto = readCommandUntilValid();
        OutputView.printEnteringRoomMessage(roomDto);
        return roomDto;
    }

    private RoomDto readCommandUntilValid() {
        try {
            return InputView.readRoomCommand().execute(roomService);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return readCommandUntilValid();
        }
    }
}
