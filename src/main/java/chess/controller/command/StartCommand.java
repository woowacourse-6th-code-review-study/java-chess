package chess.controller.command;

import chess.dto.BoardSnapShotDto;
import chess.service.ChessGameService;
import chess.view.OutputView;

public class StartCommand implements Command {
    @Override
    public ExecuteResult execute(ChessGameService chessGameService, OutputView outputView) {
        BoardSnapShotDto boardSnapShotDto = chessGameService.startChessGame();
        outputView.printChessBoardMessage(boardSnapShotDto);
        return new ExecuteResult(true, true);
    }
}
