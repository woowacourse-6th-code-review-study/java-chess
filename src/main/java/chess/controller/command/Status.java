package chess.controller.command;

import chess.controller.State;
import chess.service.BoardService;
import chess.service.GameService;
import chess.view.OutputView;
import java.util.List;

public class Status implements Command {

    private static final int STATUS_COMMAND_SIZE = 1;

    public Status(List<String> commandInput) {
        if (commandInput.size() != STATUS_COMMAND_SIZE) {
            throw new IllegalArgumentException("게임 점수 명령어 입력 형식이 올바르지 않습니다.");
        }
    }

    @Override
    public State execute(GameService gameService, BoardService boardService, Long roomId) {
        OutputView.printTeamScore(gameService.generateGameResult(roomId));
        return State.RUNNING;
    }
}
