package chess.controller;

import chess.controller.command.Command;
import chess.controller.command.CommandRouter;
import chess.domain.game.ChessGame;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessGameController {

    public void run() {
        ChessGame chessGame = new ChessGame();
        OutputView.printStartMessage();
        process(chessGame);
    }

    private void process(ChessGame chessGame) {
        State state = State.RUNNING;
        do {
            state = executeCommand(chessGame, state);
        } while (state != State.END);
    }

    private State executeCommand(ChessGame chessGame, State state) {
        try {
            Command command = CommandRouter.findCommendByInput(InputView.readCommend());
            return command.execute(chessGame);
        } catch (RuntimeException error) {
            OutputView.printError(error);
            return state;
        }
    }
}
