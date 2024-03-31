import controller.ChessGameController;
import controller.GameRoomController;
import dto.RoomDto;
import service.ChessGameService;
import service.GameRoomService;

public class Application {
    public static void main(String[] args) {
        GameRoomController gameRoomController = new GameRoomController(new GameRoomService());
        ChessGameController chessGameController = new ChessGameController(new ChessGameService());

        while (true) {
            RoomDto roomDto = gameRoomController.run();
            chessGameController.start(roomDto);
        }
    }
}
