package chess.controller.command;

import chess.controller.State;
import chess.domain.game.ChessGame;
import java.util.List;

public class End implements Command {

    private static final int END_COMMAND_SIZE = 1;

    public End(List<String> commandInput) {
        if (commandInput.size() != END_COMMAND_SIZE) {
            throw new IllegalArgumentException("게임 종료 명령어 입력 형식이 올바르지 않습니다.");
        }
    }

    @Override
    public State execute(ChessGame chessGame) {
        return State.END;
    }
}
