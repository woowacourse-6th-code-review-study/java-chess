package chess.controller;

import chess.controller.command.Command;
import chess.controller.command.ExecuteResult;
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
        chessGameService.startChessGame();

        ExecuteResult result;
        do {
            Command command = inputView.readCommand();
            result = command.execute(chessGameService, outputView);
        }
        while (result.isSuccess() && result.isNeedNextCommand());
    }
}
