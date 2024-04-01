package chess;

import chess.controller.ChessGameController;
import chess.view.CommandParser;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessApplication {
    public static void main(String[] args) {
        InputView inputView = new InputView(new CommandParser());
        OutputView outputView = new OutputView();

        ChessGameController controller = new ChessGameController(inputView, outputView);
        controller.run();
    }
}
