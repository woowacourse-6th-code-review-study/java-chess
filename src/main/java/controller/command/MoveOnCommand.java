package controller.command;

import domain.board.ChessBoard;
import domain.position.Position;
import view.OutputView;

import java.util.List;

public class MoveOnCommand implements Command {
    private static final int ARGUMENT_SIZE = 2;

    private final Position source;
    private final Position target;

    public MoveOnCommand(final List<String> arguments) {
        if (arguments.size() != ARGUMENT_SIZE) {
            throw new IllegalArgumentException();
        }
        this.source = new Position(arguments.get(0));
        this.target = new Position(arguments.get(1));
    }

    @Override
    public void execute(final ChessBoard board, final OutputView outputView) {
        try {
            board.move(source, target);
            outputView.printBoard(board);
        } catch (Exception e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    @Override
    public boolean isNotEnded() {
        return true;
    }
}
