package chess;

import chess.controller.ChessGameController;
import chess.repository.ConnectionManager;
import chess.repository.PieceRepository;
import chess.repository.TurnRepository;
import chess.service.ChessGameService;
import chess.view.CommandParser;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessApplication {
    public static void main(String[] args) {
        InputView inputView = new InputView(new CommandParser());
        OutputView outputView = new OutputView();
        PieceRepository pieceRepository = new PieceRepository(new ConnectionManager());
        TurnRepository turnRepository = new TurnRepository(new ConnectionManager());
        ChessGameService chessGameService = new ChessGameService(pieceRepository, turnRepository);

        ChessGameController controller = new ChessGameController(inputView, outputView, chessGameService);
        controller.run();
    }
}
