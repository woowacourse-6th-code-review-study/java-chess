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
        Team winner = selectWinnerTeam(whiteScore, blackScore);
        outputView.printStatusMessage(whiteScore, blackScore, winner);
        return new ExecuteResult(true, true);
    }

    private Team selectWinnerTeam(Score whiteScore, Score blackScore) {
        if (whiteScore.isAbove(blackScore)) {
            return Team.WHITE;
        }
        return Team.BLACK;
    }
}
