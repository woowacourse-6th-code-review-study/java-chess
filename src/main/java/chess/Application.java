package chess;

import chess.controller.ChessGameController;
import chess.repository.BoardRepository;
import chess.repository.BoardRepositoryImpl;
import chess.repository.RoomRepository;
import chess.repository.RoomRepositoryImpl;
import chess.service.BoardService;
import chess.service.GameService;

public class Application {
    public static void main(String[] args) {
        RoomRepository roomRepository = new RoomRepositoryImpl();
        BoardRepository boardRepository = new BoardRepositoryImpl();

        GameService gameService = new GameService(roomRepository, boardRepository);
        BoardService boardService = new BoardService(roomRepository, boardRepository);
        ChessGameController chessGameController = new ChessGameController(gameService, boardService);
        chessGameController.run();
    }
}
