package controller.command;

import domain.board.ChessBoard;
import view.OutputView;

import java.util.List;

public class EndOnCommand implements Command {
    private static final int ARGUMENT_SIZE = 0;

    public EndOnCommand() {
        this(List.of());
    }

    public EndOnCommand(final List<String> arguments) {
        if (arguments.size() != ARGUMENT_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void execute(final ChessBoard board, final OutputView outputView) {
        outputView.printScore(board.calculateScore());
    }

    @Override
    public boolean isNotEnded() {
        return false;
    }
}
