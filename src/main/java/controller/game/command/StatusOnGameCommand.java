package controller.game.command;

import domain.ChessGame;
import domain.board.Score;
import view.OutputView;

import java.util.List;

public class StatusOnGameCommand implements GameCommand {
    public StatusOnGameCommand(final List<String> arguments) {
        validateArgumentSize(arguments);
    }

    private void validateArgumentSize(final List<String> arguments) {
        if (!arguments.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void execute(final ChessGame game) {
        final Score score = game.getScore();
        OutputView.printScore(score);
    }
}
