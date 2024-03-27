package controller.command;

import domain.board.ChessBoard;
import view.OutputView;

import java.util.List;

public class StartOnCommand implements Command {
    private static final int ARGUMENT_SIZE = 0;

    public StartOnCommand() {
        this(List.of());
    }

    public StartOnCommand(final List<String> arguments) {
        if (arguments.size() != ARGUMENT_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void execute(final ChessBoard board, final OutputView outputView) {
        outputView.printBoard(board);
    }

    @Override
    public boolean isNotEnded() {
        return true;
    }
}
