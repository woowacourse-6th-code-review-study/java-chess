package controller.command;

import domain.ChessGame;
import domain.board.Score;
import domain.position.Position;
import view.OutputView;

import java.util.List;

public class MoveOnCommand implements Command {
    private static final int ARGUMENT_SIZE = 2;

    private final Position source;
    private final Position target;

    public MoveOnCommand(final List<String> arguments) {
        validateArgumentSize(arguments);
        this.source = new Position(arguments.get(0));
        this.target = new Position(arguments.get(1));
    }

    private void validateArgumentSize(final List<String> arguments) {
        if (arguments.size() != ARGUMENT_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void execute(final ChessGame game) {
        game.move(source, target);
        OutputView.printBoard(game.getBoard());

        if (game.isGameOver()) {
            final Score score = game.getScore();
            OutputView.printScore(score);
            OutputView.printWinner(score);
        }
    }
}
