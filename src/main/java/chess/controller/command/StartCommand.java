package chess.controller.command;

import chess.dto.BoardDto;
import chess.service.ChessGameService;
import chess.view.OutputView;

public class StartCommand implements Command {
    @Override
    public ExecuteResult execute(ChessGameService chessGameService, OutputView outputView) {
        BoardDto boardDto = chessGameService.startChessGame();
        outputView.printChessBoardMessage(boardDto);
        return new ExecuteResult(true, true);
    }
}
