package controller.command;

import domain.board.ChessBoard;
import view.OutputView;

import java.util.List;

public class EndOnCommand implements Command {
    private static final int ARGUMENT_SIZE = 0;

    public EndOnCommand(final List<String> arguments) {
        validateArgumentSize(arguments);
    }

    private void validateArgumentSize(final List<String> arguments) {
        if (arguments.size() != ARGUMENT_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void execute(final ChessBoard board, final OutputView outputView) {
        board.end();
        outputView.printScore(board.calculateScore());
    }
}
