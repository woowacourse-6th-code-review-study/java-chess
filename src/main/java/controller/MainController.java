package controller;

import controller.game.ChessGameController;
import controller.room.GameRoomController;
import dto.RoomDto;
import service.ChessGameService;
import service.GameRoomService;

public class MainController {
    private final GameRoomController gameRoomController = new GameRoomController(new GameRoomService());
    private final ChessGameController chessGameController = new ChessGameController(new ChessGameService());

    public void run() {
        while (true) {
            RoomDto roomDto = gameRoomController.run();
            chessGameController.start(roomDto);
        }
    }
}
