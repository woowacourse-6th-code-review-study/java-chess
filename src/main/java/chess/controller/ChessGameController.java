package chess.controller;

import chess.controller.command.Command;
import chess.controller.command.ExecuteResult;
import chess.domain.ChessGame;
import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardCreator;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessGameController {
    private final InputView inputView;
    private final OutputView outputView;

    public ChessGameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printStartMessage();
        ChessGame chessGame = initializeChessGame();

        ExecuteResult result;
        do {
            Command command = inputView.readCommand();
            result = command.execute(chessGame, outputView);
        }
        while (result.isSuccess() && result.isNeedNextCommand());
    }

    private ChessGame initializeChessGame() {
        ChessBoardCreator chessBoardCreator = new ChessBoardCreator();
        ChessBoard chessBoard = chessBoardCreator.create();
        return new ChessGame(chessBoard);
    }
}
