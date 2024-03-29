package chess.controller.command;

import chess.domain.ChessGame;
import chess.service.ChessGameService;
import chess.view.OutputView;

public class StartCommand implements Command {
    @Override
    public ExecuteResult execute(ChessGameService chessGameService, ChessGame chessGame, OutputView outputView) {
        outputView.printChessBoardMessage(chessGame.getChessBoard());
        return new ExecuteResult(true, true);
    }
}
