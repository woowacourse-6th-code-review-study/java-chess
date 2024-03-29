package chess.controller.command;

import chess.controller.State;
import chess.domain.game.ChessGame;
import chess.domain.game.Score;
import chess.domain.piece.Color;
import chess.view.OutputView;
import java.util.List;
import java.util.Map;

public class Status implements Command {

    private static final int STATUS_COMMAND_SIZE = 1;

    public Status(List<String> commandInput) {
        if (commandInput.size() != STATUS_COMMAND_SIZE) {
            throw new IllegalArgumentException("게임 점수 명령어 입력 형식이 올바르지 않습니다.");
        }
    }

    @Override
    public State execute(ChessGame chessGame) {
        Map<Color, Score> teamScore = chessGame.calculateScore();
        OutputView.printTeamScore(teamScore.get(Color.WHITE), teamScore.get(Color.BLACK));
        return State.RUNNING;
    }
}
