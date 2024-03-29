package chess.controller.command;

import chess.domain.ChessGame;
import chess.domain.position.Position;
import chess.service.ChessGameService;
import chess.view.OutputView;

public class MoveCommand implements Command {
    private final Position start;
    private final Position destination;

    public MoveCommand(Position start, Position destination) {
        this.start = start;
        this.destination = destination;
    }

    @Override
    public ExecuteResult execute(ChessGameService chessGameService, ChessGame chessGame, OutputView outputView) {
        chessGame.move(start, destination);
        chessGameService.saveChessGame(chessGame);
        outputView.printChessBoardMessage(chessGame.getChessBoard());
        return new ExecuteResult(true, true);
    }
}
