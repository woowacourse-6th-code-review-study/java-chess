package controller.game.command;

import domain.ChessGame;
import domain.board.Score;
import view.OutputView;

import java.util.List;

public class StatusOnGameCommand implements GameCommand {
    private static final int ARGUMENT_SIZE = 0;

    public StatusOnGameCommand(final List<String> arguments) {
        validateArgumentSize(arguments);
    }

    private void validateArgumentSize(final List<String> arguments) {
        if (arguments.size() != ARGUMENT_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void execute(final ChessGame game) {
        final Score score = game.getScore();
        OutputView.printScore(score);
    }
}