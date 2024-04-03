package controller.game.command;

import domain.ChessGame;

import java.util.List;

public class EndOnGameCommand implements GameCommand {
    public EndOnGameCommand(final List<String> arguments) {
        validateArgumentSize(arguments);
    }

    private void validateArgumentSize(final List<String> arguments) {
        if (!arguments.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void execute(final ChessGame game) {
        game.end();
    }
}
