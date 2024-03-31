package chess.controller.command;

import chess.controller.State;
import chess.service.BoardService;
import chess.service.GameService;

public interface Command {

    State execute(GameService gameService, BoardService boardService, Long roomId);
}
