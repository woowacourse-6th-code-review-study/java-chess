package chess.controller.command;

import chess.domain.ChessGame;
import chess.service.ChessGameService;
import chess.view.OutputView;

public interface Command {
    ExecuteResult execute(ChessGameService chessGameService, ChessGame chessGame, OutputView outputView);
}
