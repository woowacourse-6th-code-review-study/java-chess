package controller.game.command;

import domain.ChessGame;
import view.OutputView;

import java.util.List;

public class StartOnCommand implements Command {
    private static final int ARGUMENT_SIZE = 0;

    public StartOnCommand(final List<String> arguments) {
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
