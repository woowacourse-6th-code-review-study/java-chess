package controller.game.command;

import domain.ChessGame;
import view.OutputView;

import java.util.List;

public class StartOnGameCommand implements GameCommand {
    private static final int ARGUMENT_SIZE = 0;

    public StartOnGameCommand(final List<String> arguments) {
        validateArgumentSize(arguments);
    }

    private void validateArgumentSize(final List<String> arguments) {
        if (arguments.size() != ARGUMENT_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void execute(final ChessGame game) {
        game.start();
        OutputView.printBoard(game.getBoard());
    }
}
