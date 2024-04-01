package controller;

import controller.game.ChessGameController;
import controller.room.GameRoomController;
import controller.user.UserController;
import database.dao.GameStateDaoImpl;
import database.dao.PieceDaoImpl;
import database.dao.RoomDaoImpl;
import dto.RoomDto;
import dto.UserDto;
import service.ChessGameService;
import service.GameRoomService;

public class MainController {
    private final GameRoomController gameRoomController = new GameRoomController(new GameRoomService(new RoomDaoImpl()));
    private final ChessGameController chessGameController = new ChessGameController(new ChessGameService(
            new PieceDaoImpl(), new GameStateDaoImpl()));
    private final UserController userController = new UserController(new UserService(new UserDaoImpl()));

    public void run() {
        UserDto user = userController.run();
        while (true) {
            RoomDto roomDto = gameRoomController.run(user);
            chessGameController.run(roomDto);
        }
    }
}
