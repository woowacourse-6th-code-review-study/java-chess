package controller.command;

import domain.board.ChessBoard;
import domain.board.GameStatus;
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
    public void execute(final ChessBoard board, final OutputView outputView) {
        validateGameStatus(board);
        try {
            board.move(source, target);
            outputView.printBoard(board);
        } catch (Exception e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void validateGameStatus(final ChessBoard board) {
        if (board.getStatus() == GameStatus.NOT_STARTED) {
            throw new IllegalStateException("게임을 먼저 시작해 주세요.");
        }
        if (board.getStatus() == GameStatus.ENDED || board.getStatus() == GameStatus.KING_IS_DEAD) {
            throw new IllegalStateException("게임이 종료됐습니다.");
        }
    }
}
