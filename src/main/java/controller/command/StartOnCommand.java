package controller.command;

import domain.board.ChessBoard;
import domain.board.GameStatus;
import view.OutputView;

import java.util.List;

public class StartOnCommand implements Command {
    private static final int ARGUMENT_SIZE = 0;

    public StartOnCommand() {
        this(List.of());
    }

    public StartOnCommand(final List<String> arguments) {
        validateArgumentSize(arguments);
    }

    private void validateArgumentSize(final List<String> arguments) {
        if (arguments.size() != ARGUMENT_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void execute(final ChessBoard board, final OutputView outputView) {
        validateGameStatus(board);
        board.start();
        outputView.printBoard(board);
    }

    private void validateGameStatus(final ChessBoard board) {
        if (board.getStatus() != GameStatus.NOT_STARTED) {
            throw new IllegalStateException("게임이 이미 시작됐습니다.");
        }
    }
}
