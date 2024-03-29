package chess.controller;

import chess.controller.command.Command;
import chess.controller.command.ExecuteResult;
import chess.domain.ChessGame;
import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardCreator;
import chess.service.ChessGameService;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessGameController {
    private final InputView inputView;
    private final OutputView outputView;
    private final ChessGameService chessGameService;

    public ChessGameController(InputView inputView, OutputView outputView, ChessGameService chessGameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.chessGameService = chessGameService;
    }

    public void run() {
        outputView.printStartMessage();
        ChessGame chessGame = initializeChessGame();
        if (chessGameService.isChessGameInProgress()) {
            chessGame = chessGameService.loadChessGame();
        }
        ExecuteResult result;
        do {
            Command command = inputView.readCommand();
            result = command.execute(chessGame, outputView);
            chessGameService.saveChessGame(chessGame);
        }
        while (result.isSuccess() && result.isNeedNextCommand() && chessGame.isNotEnd());
    }

    private ChessGame initializeChessGame() {
        ChessBoardCreator chessBoardCreator = new ChessBoardCreator();
        ChessBoard chessBoard = chessBoardCreator.create();
        return new ChessGame(chessBoard);
    }
}
