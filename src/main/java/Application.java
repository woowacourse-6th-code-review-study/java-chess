import controller.ChessController;
import repository.ChessGameService;

public class Application {
    public static void main(String[] args) {
        ChessController chessController = new ChessController(new ChessGameService());
        chessController.start();
    }
}
