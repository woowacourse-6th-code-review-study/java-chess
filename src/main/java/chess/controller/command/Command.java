package chess.controller.command;

import chess.controller.State;
import chess.domain.game.ChessGame;

public interface Command {

    State execute(ChessGame chessGame);
}
