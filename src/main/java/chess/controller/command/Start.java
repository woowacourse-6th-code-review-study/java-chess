package chess.controller.command;

import chess.controller.State;
import chess.domain.game.ChessGame;
import chess.view.OutputView;
import java.util.List;

public class Start implements Command {

    private static final int START_COMMAND_SIZE = 1;

    public Start(List<String> commandInput) {
        if (commandInput.size() != START_COMMAND_SIZE) {
            throw new IllegalArgumentException("게임 시작 명령어 입력 형식이 올바르지 않습니다.");
        }
    }

    @Override
    public State execute(ChessGame chessGame) {
        OutputView.printBoard(chessGame.getBoard());
        return State.RUNNING;
    }
}
