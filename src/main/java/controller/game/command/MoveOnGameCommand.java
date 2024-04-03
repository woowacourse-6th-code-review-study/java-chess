package controller.game.command;

import domain.ChessGame;
import domain.board.Score;
import domain.position.Position;
import view.OutputView;

import java.util.List;
import java.util.regex.Pattern;

public class MoveOnGameCommand implements GameCommand {
    private static final Pattern POSITION_INPUT_PATTERN = Pattern.compile("^[A-H][1-8]$");
    private static final int ARGUMENT_SIZE = 2;

    private final Position source;
    private final Position target;

    public MoveOnGameCommand(final List<String> arguments) {
        validateArgumentSize(arguments);
        this.source = new Position(arguments.get(0));
        this.target = new Position(arguments.get(1));
    }

    private void validateArgumentSize(final List<String> arguments) {
        if (arguments.size() != ARGUMENT_SIZE) {
            throw new IllegalArgumentException();
        }
        validatePositions(arguments);
    }

    private void validatePositions(final List<String> inputs) {
        inputs.forEach(this::validatePositionFormat);
    }

    private void validatePositionFormat(final String positionInput) {
        if (!POSITION_INPUT_PATTERN.matcher(positionInput).matches()) {
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
