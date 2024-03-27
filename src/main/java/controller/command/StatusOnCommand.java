package controller.command;

import domain.board.ChessBoard;
import domain.board.Score;
import view.OutputView;

import java.util.List;

public class StatusOnCommand implements Command {
    private static final int ARGUMENT_SIZE = 0;

    public StatusOnCommand(final List<String> arguments) {
        if (arguments.size() != ARGUMENT_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void execute(final ChessBoard board, final OutputView outputView) {
        final Score score = board.calculateScore();
        outputView.printScore(score);
    }

    @Override
    public boolean isNotEnded() {
        return true;
    }
}
