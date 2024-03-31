package chess.controller.command;

import chess.service.ChessGameService;
import chess.view.OutputView;

public class EndCommand implements Command {
    @Override
    public ExecuteResult execute(ChessGameService chessGameService, OutputView outputView) {
        return new ExecuteResult(true, false);
    }
}
