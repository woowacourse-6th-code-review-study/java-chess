package chess.controller.command;

import chess.domain.ChessGame;
import chess.view.OutputView;

public interface Command {
    ExecuteResult execute(ChessGame chessGame, OutputView outputView);
}
