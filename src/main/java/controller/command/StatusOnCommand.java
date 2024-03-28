package controller.command;

import domain.board.ChessBoard;
import domain.board.GameStatus;
import domain.board.Score;
import view.OutputView;

import java.util.List;

public class StatusOnCommand implements Command {
    private static final int ARGUMENT_SIZE = 0;

    public StatusOnCommand(final List<String> arguments) {
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
        final Score score = board.calculateScore();
        outputView.printScore(score);
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
