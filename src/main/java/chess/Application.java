package chess;

import chess.controller.ChessGameController;
import chess.service.BoardService;
import chess.service.GameService;

public class Application {
    public static void main(String[] args) {
        GameService gameService = new GameService();
        BoardService boardService = new BoardService();
        ChessGameController chessGameController = new ChessGameController(gameService, boardService);
        chessGameController.run();
    }
}
