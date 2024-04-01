package controller;

import controller.game.ChessGameController;
import controller.room.GameRoomController;
import dto.RoomDto;
import repository.GameStateDaoImpl;
import repository.PieceDaoImpl;
import repository.RoomDaoImpl;
import service.ChessGameService;
import service.GameRoomService;

public class MainController {
    private final GameRoomController gameRoomController = new GameRoomController(new GameRoomService(new RoomDaoImpl()));
    private final ChessGameController chessGameController = new ChessGameController(new ChessGameService(
            new PieceDaoImpl(), new GameStateDaoImpl()));

    public void run() {
        while (true) {
            RoomDto roomDto = gameRoomController.run();
            chessGameController.start(roomDto);
        }
    }
}
