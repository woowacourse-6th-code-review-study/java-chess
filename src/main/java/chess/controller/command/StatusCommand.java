package chess.controller.command;

import chess.domain.ChessGame;
import chess.domain.piece.Score;
import chess.domain.piece.Team;
import chess.view.OutputView;

public class StatusCommand implements Command {

    @Override
    public ExecuteResult execute(ChessGame chessGame, OutputView outputView) {
        Score whiteScore = chessGame.calculateTeamScore(Team.WHITE);
        Score blackScore = chessGame.calculateTeamScore(Team.BLACK);
        outputView.printTeamStatusMessage(Team.WHITE, whiteScore);
        outputView.printTeamStatusMessage(Team.BLACK, blackScore);
        return new ExecuteResult(true, true);
    }
}
