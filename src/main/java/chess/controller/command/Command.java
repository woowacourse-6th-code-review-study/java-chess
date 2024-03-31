package chess.controller.command;

import chess.service.ChessGameService;
import chess.view.OutputView;

public interface Command {
    ExecuteResult execute(ChessGameService chessGameService, OutputView outputView);
}
