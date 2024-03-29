package chess.controller.command;

import chess.domain.ChessGame;
import chess.service.ChessGameService;
import chess.view.OutputView;

public class EndCommand implements Command {
    @Override
    public ExecuteResult execute(ChessGameService chessGameService, ChessGame chessGame, OutputView outputView) {
        chessGameService.saveChessGame(chessGame);
        return new ExecuteResult(true, false);
    }
}
