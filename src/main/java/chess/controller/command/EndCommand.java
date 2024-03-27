package chess.controller.command;

import chess.domain.ChessGame;
import chess.view.OutputView;

public class EndCommand implements Command {
    @Override
    public ExecuteResult execute(ChessGame chessGame, OutputView outputView) {
        return new ExecuteResult(true, false);
    }
}
